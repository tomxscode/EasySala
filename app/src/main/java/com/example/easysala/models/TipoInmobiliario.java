package com.example.easysala.models;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TipoInmobiliario implements CallbackTipoinmobiliario {

    private String IdTipoInmobiliario;
    private String NombreTipoInmobiliario;
    private String Descripcion;

    public TipoInmobiliario(String idTipoInmobiliario, String nombreTipoInmobiliario, String descripcion) {
        IdTipoInmobiliario = idTipoInmobiliario;
        NombreTipoInmobiliario = nombreTipoInmobiliario;
        Descripcion = descripcion;
    }

    public TipoInmobiliario(String idTipoInmobiliario){IdTipoInmobiliario = idTipoInmobiliario;}

    public String getIdTipoInmobiliario() {
        return IdTipoInmobiliario;
    }

    public void setIdTipoInmobiliario(String idTipoInmobiliario) {
        IdTipoInmobiliario = idTipoInmobiliario;
    }

    public String getNombreTipoInmobiliario() {
        return NombreTipoInmobiliario;
    }

    public void setNombreTipoInmobiliario(String nombreTipoInmobiliario) {
        NombreTipoInmobiliario = nombreTipoInmobiliario;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public void obtenerInfo(CallbackTipoinmobiliario callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(("tipo_inmobiliario"))
                .document(getIdTipoInmobiliario())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {

                            DocumentSnapshot document = task.getResult();

                            if (document.exists()) {
                                NombreTipoInmobiliario = document.getString("nombre");
                                Descripcion = document.getString("descripcion");
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
