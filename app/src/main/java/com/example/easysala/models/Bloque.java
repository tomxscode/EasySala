package com.example.easysala.models;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class Bloque implements CallbackBloque {

    private String IdBloque;
    private String HoraInicio;
    private String HoraFin;

    public Bloque(String idBloque, String horaInicio, String horaFin) {
        IdBloque = idBloque;
        HoraInicio = horaInicio;
        HoraFin = horaFin;
    }

    public Bloque(String idBloque){IdBloque = idBloque;}

    public String getIdBloque() {
        return IdBloque;
    }

    public void setIdBloque(String idBloque) {
        IdBloque = idBloque;
    }

    public String getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(String horaInicio) {
        HoraInicio = horaInicio;
    }

    public String getHoraFin() {
        return HoraFin;
    }

    public void setHoraFin(String horaFin) {
        HoraFin = horaFin;
    }

    public void obtenerInfo(CallbackBloque callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(("bloque"))
                .document(getIdBloque())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {

                            DocumentSnapshot document = task.getResult();

                            if (document.exists()) {

                                HoraInicio = document.getString("hora_inicio");
                                HoraFin = document.getString("hora_fin");

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
