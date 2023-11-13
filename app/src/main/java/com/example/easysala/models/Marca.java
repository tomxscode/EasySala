package com.example.easysala.models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayoutStates;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Marca implements CallbackMarca{

    private String IdMarca;
    private String NombreMarca;

    public Marca(String idMarca, String nombreMarca) {
        IdMarca = idMarca;
        NombreMarca = nombreMarca;
    }

    public String getIdMarca() {
        return IdMarca;
    }

    public void setIdMarca(String idMarca) {
        IdMarca = idMarca;
    }

    public String getNombreMarca() {
        return NombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        NombreMarca = nombreMarca;
    }

    public void obtenerInfo(CallbackMarca callback) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(("marca_implemento"))
                .document(getIdMarca())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {

                            DocumentSnapshot document = task.getResult();

                            if (document.exists()) {

                                NombreMarca = document.getString("nombre");
                                Log.d(ConstraintLayoutStates.TAG, "Documento encontrado");
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
