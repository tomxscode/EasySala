package com.example.easysala.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.ImmutableList;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Implementos  implements CallbackImplemento{

    private String IdImplemento;
    private String NombreImplemento;
    private String Ubicacion;
    private int Cantidad;
    private String Descripcion;

    private Modelo ModeloImplemento;

    private TipoImplemento TipoImplemento;

    public Implementos(String idImplemento, String nombreImplemento, String ubicacion, int cantidad, String descripcion, Modelo modeloImplemento, com.example.easysala.models.TipoImplemento tipoImplemento) {
        IdImplemento = idImplemento;
        NombreImplemento = nombreImplemento;
        Ubicacion = ubicacion;
        Cantidad = cantidad;
        Descripcion = descripcion;
        ModeloImplemento = modeloImplemento;
        TipoImplemento = tipoImplemento;
    }
    public Implementos(String idImplemento){IdImplemento = idImplemento;}
    public String getIdImplemento() {
        return IdImplemento;
    }

    public void setIdImplemento(String idImplemento) {
        IdImplemento = idImplemento;
    }

    public String getNombreImplemento() {
        return NombreImplemento;
    }

    public void setNombreImplemento(String nombreImplemento) {
        NombreImplemento = nombreImplemento;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Modelo getModeloImplemento() {
        return ModeloImplemento;
    }

    public void setModeloImplemento(Modelo modeloImplemento) {
        ModeloImplemento = modeloImplemento;
    }

    public com.example.easysala.models.TipoImplemento getTipoImplemento() {
        return TipoImplemento;
    }

    public void setTipoImplemento(com.example.easysala.models.TipoImplemento tipoImplemento) {
        TipoImplemento = tipoImplemento;
    }
    public void obtenerInfo(CallbackImplemento callback){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("implemento").document(this.getIdImplemento());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        setNombreImplemento(document.getString("nombre"));
                        setUbicacion(document.getString("ubicacion"));
                        String documentTipo = document.getString("tipo");
                        TipoImplemento tipo = new TipoImplemento(documentTipo);
                        String documentModelo = document.getString("modelo");
                        Modelo modelo = new Modelo(documentModelo);

                        modelo.obtenerInfo(new CallbackModelo() {
                            @Override
                            public void onError(String mensaje) {
                                callback.onError(mensaje);  // Manejar errores relacionados con la obtención del modelo
                                callback.onInfoCargada(false);
                            }

                            @Override
                            public void onInfoCargada(boolean estado) {

                                if (estado) {
                                    setModeloImplemento(modelo);
                                    callback.onInfoCargada(true);
                                } else {
                                    Log.d("TAG" , "nOM SE ENCONTRO NAD ");
                                    setModeloImplemento(null);
                                    callback.onInfoCargada(false);
                                }

                                // Llamada al callback para indicar que la información del modelo se cargó

                            }
                        });

                        tipo.obtenerInfo(new CallbackTipoImplemento() {
                            @Override
                            public void onError(String mensaje) {

                            }

                            @Override
                            public void InfoCargada(boolean estado) {
                                if(estado){
                                    setTipoImplemento(tipo);
                                }else{
                                    setTipoImplemento(null);
                                }
                            }
                        });
                    } else {
                        // Llamada al callback para indicar que la información no fue cargada
                        callback.onInfoCargada(false);
                    }
                } else {
                    // Llamada al callback para manejar errores generales de consulta
                    callback.onError("Error en consulta: " + task.getException().getMessage());
                    callback.onInfoCargada(false);
                }
            }
        });
    }




    @Override
    public void onError(String mensaje) {

    }

    @Override
    public void onInfoCargada(boolean estado) {

    }
}
