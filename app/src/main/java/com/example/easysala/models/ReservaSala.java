package com.example.easysala.models;

import android.icu.text.DateFormat;

import java.util.Date;

public class ReservaSala {
    private String documentId;
    private Date fechaSolicitud;
    private Date fechaReserva;
    private String horaReserva;
    private String horaFinReserva;
    private Salas sala;
    private  Usuarios usuario;
    private boolean aprobada;

    public ReservaSala(String documentId, Date fechaSolicitud, Date fechaReserva, String horaReserva, String horaFinReserva, Salas sala, Usuarios usuario, boolean aprobada) {
        this.documentId = documentId;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaReserva = fechaReserva;
        this.horaReserva = horaReserva;
        this.horaFinReserva = horaFinReserva;
        this.sala = sala;
        this.usuario = usuario;
        this.aprobada = aprobada;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getHoraReserva() {
        return horaReserva;
    }

    public void setHoraReserva(String horaReserva) {
        this.horaReserva = horaReserva;
    }

    public String getHoraFinReserva() {
        return horaFinReserva;
    }

    public void setHoraFinReserva(String horaFinReserva) {
        this.horaFinReserva = horaFinReserva;
    }

    public Salas getSala() {
        return sala;
    }

    public void setSala(Salas sala) {
        this.sala = sala;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public boolean isAprobada() {
        return aprobada;
    }

    public void setAprobada(boolean aprobada) {
        this.aprobada = aprobada;
    }
}
