package com.example.easysala.models;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TipoSala  implements  CallbackTipoSala{

    private String IdTipoSala;
    private String NombreTipoSala;
    private String Descripcion;

    public TipoSala(String idTipoSala, String nombreTipoSala, String descripcion) {
        IdTipoSala = idTipoSala;
        NombreTipoSala = nombreTipoSala;
        Descripcion = descripcion;
    }

    public TipoSala(String idTipoSala){IdTipoSala = idTipoSala;}

    public String getIdTipoSala() {
        return IdTipoSala;
    }

    public void setIdTipoSala(String idTipoSala) {
        IdTipoSala = idTipoSala;
    }

    public String getNombreTipoSala() {
        return NombreTipoSala;
    }

    public void setNombreTipoSala(String nombreTipoSala) {
        NombreTipoSala = nombreTipoSala;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public void obtenerInfo(CallbackTipoSala callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(("tipo_sala"))
                .document(getIdTipoSala())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {

                            DocumentSnapshot document = task.getResult();

                            if (document.exists()) {
                                NombreTipoSala = document.getString("nombre");
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
