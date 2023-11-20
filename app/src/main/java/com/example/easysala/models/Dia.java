package com.example.easysala.models;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Dia implements CallbackDia {

    private String IdDia;
    private String nombre;

    public Dia(String idDia, String nombre) {
        IdDia = idDia;
        this.nombre = nombre;
    }

    public Dia(String idDia){IdDia = idDia;}

    public String getIdDia() {
        return IdDia;
    }

    public void setIdDia(String idDia) {
        IdDia = idDia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void obtenerInfo(CallbackDia callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(("dia"))
                .document(getIdDia())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {

                            DocumentSnapshot document = task.getResult();

                            if (document.exists()) {
                                nombre = document.getString("nombre");
                                callback.onObtenerInfo(true);
                            } else {
                                callback.onObtenerInfo(false);
                            }

                        } else {
                            callback.onError("Error en consulta");
                        }

                    }
                });
    }


    @Override
    public void onError(String error) {

    }

    @Override
    public void onObtenerInfo(boolean encontrado) {

    }
}
