package com.example.easysala.models;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Reserva implements CallbackReservaSala {
    private String documentId;
    private boolean aprobada;

    private Horario horario;

    private Usuarios usuario;

    public Reserva(String documentId, boolean aprobada, Horario horario, Usuarios usuario) {
        this.documentId = documentId;
        this.aprobada = aprobada;
        this.horario = horario;
        this.usuario = usuario;
    }


    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public boolean isAprobada() {
        return aprobada;
    }

    public void setAprobada(boolean aprobada) {
        this.aprobada = aprobada;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }
    public void crearReserva(CallbackReservaSala callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reservaImplementoRef = db.collection("reserva_sala");


        Map<String, Object> reservaData = new HashMap<>();
        reservaData.put("aprobada", aprobada);
        reservaData.put("horario", horario.getIdHorario());
        reservaData.put("usuario", usuario.getUid_bd());

        reservaImplementoRef.add(reservaData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        callback.onReservaRealizada(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onReservaRealizada(false);
                        callback.onError("Error al crear la reserva: " + e.getMessage());
                    }
                });
    }

    @Override
    public void onError(String mensaje) {

    }

    @Override
    public void onInfoEncontrada(boolean estado) {

    }

    @Override
    public void onReservaRealizada(boolean estado) {

    }
}


