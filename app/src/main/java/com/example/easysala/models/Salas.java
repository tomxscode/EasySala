package com.example.easysala.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Salas implements CallbackSala {

    private String IdSala;
    private String NombreSala;
    private int Capacidad;
    private String Descripcion;

    private TipoInmobiliario TipoInmobiliarioSala;

    private TipoSala TipoSala;

    public Salas(String idSala, String nombreSala, int capacidad, String descripcion, TipoInmobiliario tipoInmobiliarioSala, com.example.easysala.models.TipoSala tipoSala) {
        IdSala = idSala;
        NombreSala = nombreSala;
        Capacidad = capacidad;
        Descripcion = descripcion;
        TipoInmobiliarioSala = tipoInmobiliarioSala;
        TipoSala = tipoSala;
    }
    public Salas(String idSala){IdSala = idSala;}

    public TipoInmobiliario getTipoInmobiliarioSala() {
        return TipoInmobiliarioSala;
    }

    public void setTipoInmobiliarioSala(TipoInmobiliario tipoInmobiliarioSala) {
        TipoInmobiliarioSala = tipoInmobiliarioSala;
    }

    public com.example.easysala.models.TipoSala getTipoSala() {
        return TipoSala;
    }

    public void setTipoSala(com.example.easysala.models.TipoSala tipoSala) {
        TipoSala = tipoSala;
    }

    public String getIdSala() {
        return IdSala;
    }

    public void setIdSala(String idSala) {
        IdSala = idSala;
    }

    public String getNombreSala() {
        return NombreSala;
    }

    public void setNombreSala(String nombreSala) {
        NombreSala = nombreSala;
    }

    public int getCapacidad() {
        return Capacidad;
    }

    public void setCapacidad(int capacidad) {
        Capacidad = capacidad;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public void obtenerInfo(CallbackSala callback){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("sala").document(this.getIdSala());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        NombreSala = document.getString("nombre");
                        Descripcion = document.getString("descripcion");
                        String tipo_inmo = document.getString("tipo_inmobiliario");
                        String tipo_sala = document.getString("tipo_sala");
                        TipoInmobiliario tipo_in = new TipoInmobiliario(tipo_inmo);
                        TipoSala tipo_sa = new TipoSala(tipo_sala);
                        Log.d("Nombre  " ,NombreSala);

                        tipo_in.obtenerInfo(new CallbackTipoinmobiliario() {
                            @Override
                            public void onError(String mensaje) {
                                callback.onError(mensaje);  // Manejar errores relacionados con la obtención del modelo
                                callback.onObtenerInfo(false);
                            }
                            @Override
                            public void onObtenerInfo(boolean estado) {

                                if (estado) {
                                    setTipoInmobiliarioSala(tipo_in);

                                } else {
                                    setTipoInmobiliarioSala(null);
                                    callback.onObtenerInfo(false);
                                }
                                // Llamada al callback para indicar que la información del modelo se cargó
                            }
                        });

                        tipo_sa.obtenerInfo(new CallbackTipoSala() {
                            @Override
                            public void onError(String mensaje) {

                            }

                            @Override
                            public void onObtenerInfo(boolean estado) {
                                if(estado){
                                    setTipoSala(tipo_sa);
                                    callback.onObtenerInfo(true);
                                }else{
                                    setTipoSala(null);
                                }
                            }
                        });

                    } else {
                        // Llamada al callback para indicar que la información no fue cargada
                        callback.onObtenerInfo(false);
                        Log.d("Nombre  " ,NombreSala);

                    }
                } else {
                    // Llamada al callback para manejar errores generales de consulta
                    callback.onError("Error en consulta: " + task.getException().getMessage());
                    callback.onObtenerInfo(false);
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
