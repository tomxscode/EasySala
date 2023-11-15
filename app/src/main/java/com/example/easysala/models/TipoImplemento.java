package com.example.easysala.models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayoutStates;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TipoImplemento implements CallbackTipoImplemento {

    private String IdTipoImplemento;

    private String NombreTipoImplemento;

    private String Descripcion;

    public TipoImplemento(String idTipoImplemento, String nombreTipoImplemento, String descripcion) {
        IdTipoImplemento = idTipoImplemento;
        NombreTipoImplemento = nombreTipoImplemento;
        Descripcion = descripcion;
    }
    public TipoImplemento(String idTipoImplemento){IdTipoImplemento = idTipoImplemento;}

    public String getIdTipoImplemento(){
        return IdTipoImplemento;
    }

    public void setIdTipoImplemento(String idTipoImplemento) {
        IdTipoImplemento = idTipoImplemento;
    }

    public String getNombreTipoImplemento() {
        return NombreTipoImplemento;
    }

    public void setNombreTipoImplemento(String nombreTipoImplemento) {
        NombreTipoImplemento = nombreTipoImplemento;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public void obtenerInfo(CallbackTipoImplemento callback) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(("tipo_implemento"))
                .document(this.getIdTipoImplemento())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {

                            DocumentSnapshot document = task.getResult();

                            if (document.exists()) {

                                NombreTipoImplemento = document.getString("nombre");
                                Descripcion = document.getString("descripcion");
                                Log.d(ConstraintLayoutStates.TAG, "Documento encontrado" + getNombreTipoImplemento());
                                callback.InfoCargada(true);
                            } else {
                                callback.InfoCargada(false);
                            }
                        } else {
                            callback.onError("Error en consulta");
                        }

                    }
                });
    }


    @Override
    public void onError(String mensaje) {

    }

    @Override
    public void InfoCargada(boolean estado) {

    }
}
