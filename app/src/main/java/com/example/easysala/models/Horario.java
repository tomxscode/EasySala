package com.example.easysala.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Horario implements CallbackHorario {

    private String IdHorario;

    private boolean Disponibilidad;

    private Bloque BloqueHorario;

    private Dia DiaHorario;

    private Salas SalaHorario;



    public Horario(String idHorario, boolean disponibilidad, Bloque bloqueHorario, Dia diaHorario, Salas salaHorario) {
        IdHorario = idHorario;
        Disponibilidad = disponibilidad;
        BloqueHorario = bloqueHorario;
        DiaHorario = diaHorario;
        SalaHorario = salaHorario;
    }

    public Horario(String idHorario){IdHorario = idHorario;}

    public Salas getSalaHorario() {
        return SalaHorario;
    }

    public void setSalaHorario(Salas salaHorario) {
        SalaHorario = salaHorario;
    }

    public String getIdHorario() {
        return IdHorario;
    }

    public void setIdHorario(String idHorario) {
        IdHorario = idHorario;
    }

    public boolean getDisponibilidad() {
        return Disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        Disponibilidad = disponibilidad;
    }

    public Bloque getBloqueHorario() {
        return BloqueHorario;
    }

    public void setBloqueHorario(Bloque bloqueHorario) {
        BloqueHorario = bloqueHorario;
    }

    public Dia getDiaHorario() {
        return DiaHorario;
    }

    public void setDiaHorario(Dia diaHorario) {
        DiaHorario = diaHorario;
    }


    public void obtenerInfo(CallbackHorario callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("horario").document(this.getIdHorario());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()){
                        boolean f_dispo = document.getBoolean("disponibilidad");
                        String f_bloque = document.getString("bloque");
                        String f_dia = document.getString("dia");
                        String f_sala = document.getString("sala");
                        setDisponibilidad(f_dispo);
                        Bloque bloque = new Bloque(f_bloque);
                        Dia dia = new Dia(f_dia);
                        Salas sala = new Salas(f_sala);

                        Log.d("Horario", "Document data: " + document.getData());

                        bloque.obtenerInfo(new CallbackBloque() {
                            @Override
                            public void onError(String mensaje) {
                                callback.onError(mensaje);
                                callback.onObtenerInfo(false);
                            }

                            @Override
                            public void onObtenerInfo(boolean estado) {
                                if (estado) {
                                    setBloqueHorario(bloque);
                                } else {
                                    setBloqueHorario(null);
                                }
                            }
                        });

                        dia.obtenerInfo(new CallbackDia() {
                            @Override
                            public void onError(String mensaje) {
                                callback.onError(mensaje);
                                callback.onObtenerInfo(false);
                            }

                            @Override
                            public void onObtenerInfo(boolean estado) {
                                if (estado) {
                                    setDiaHorario(dia);
                                } else {
                                    setDiaHorario(null);
                                }
                            }
                        });

                        sala.obtenerInfo(new CallbackSala() {
                            @Override
                            public void onError(String error) {
                                // Puedes manejar el error aquí si es necesario
                            }

                            @Override
                            public void onObtenerInfo(boolean encontrado) {
                                if (encontrado) {
                                    setSalaHorario(sala);
                                    callback.onObtenerInfo(true);
                                } else {
                                    setSalaHorario(null);
                                }
                            }
                        });

                    } else {
                        callback.onObtenerInfo(false);
                    }
                } else {
                    callback.onError("Error en consulta: " + task.getException().getMessage());
                    callback.onObtenerInfo(false);
                }
            }
        });
    }



    @Override
    public void onError(String mensaje) {

    }

    @Override
    public void onObtenerInfo(boolean encontrado) {

    }
}
