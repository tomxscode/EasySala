package com.example.easysala.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReservaImplemento implements CallbackReservaImplemento {
    private String documentId;
    private Date fechaSolicitud;
    private Date fechaReserva;
    private Date fechaDevolucion;
    private Implementos implemento;
    private  Usuarios usuario;
    private boolean aprobada;

    private boolean entregado;

    public ReservaImplemento(Date fechaSolicitud, Date fechaReserva, Date fechaDevolucion,Implementos implemento, Usuarios usuario, boolean aprobada, boolean entregado) {
        this.fechaSolicitud = fechaSolicitud;
        this.fechaReserva = fechaReserva;
        this.fechaDevolucion = fechaDevolucion;
        this.usuario = usuario;
        this.aprobada = aprobada;
        this.entregado = entregado;
    }

    public ReservaImplemento(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public Implementos getImplemento() {
        return implemento;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public boolean isAprobada() {
        return aprobada;
    }

    public boolean isEntregado() {
        return entregado;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public void setImplemento(Implementos implemento) {
        this.implemento = implemento;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public void setAprobada(boolean aprobada) {
        this.aprobada = aprobada;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    public void onObtenerInfo(CallbackReservaImplemento callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("reserva_implemento").document(this.getDocumentId());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        fechaSolicitud = document.getDate("fecha_solicitud");
                        fechaReserva = document.getDate("fecha_reserva");
                        fechaDevolucion  = document.getDate("fecha_devolucion");
                        implemento = new Implementos(document.getString("implemento"));
                        usuario = new Usuarios(document.getString("usuario"));
                        aprobada = document.getBoolean("aprobada");
                        entregado = document.getBoolean("entregado");
                        callback.onInfoEncontrada(true);
                    } else {
                        // Llamada al callback para indicar que la información no fue cargada
                        callback.onInfoEncontrada(false);
                    }
                } else {
                    // Llamada al callback para manejar errores generales de consulta
                    callback.onError("Error en consulta: " + task.getException().getMessage());
                    callback.onInfoEncontrada(false);
                }
            }
        });
    }
    public void crearReserva(CallbackReservaImplemento callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reservaImplementoRef = db.collection("reserva_implemento");

        Log.d("USUARIO", usuario.getUid_bd());
        Log.d("IMPLEMENTO", implemento.getIdImplemento());

        // Comprobar que el id de usuario y implemento no venga nulo
        if (usuario == null || implemento == null) {
            callback.onReservaRealizada(false);
            callback.onError("Ocurrió un error al obtener los datos, re-intente.");
            return;
        }
        Map<String, Object> reservaData = new HashMap<>();
        reservaData.put("fecha_solicitud", fechaSolicitud);
        reservaData.put("fecha_reserva", fechaReserva);
        reservaData.put("fecha_devolucion", fechaDevolucion);
        reservaData.put("implemento", implemento.getIdImplemento());
        reservaData.put("usuario", usuario.getUid_bd());
        reservaData.put("aprobada", aprobada);
        reservaData.put("entregado", entregado);

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
