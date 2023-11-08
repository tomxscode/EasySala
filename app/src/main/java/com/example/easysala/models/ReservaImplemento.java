package com.example.easysala.models;

import java.util.Date;

public class ReservaImplemento {
    private String documentId;
    private Date fechaSolicitud;
    private Date fechaReserva;
    private Date fechaDevolucion;
    private Implementos implemento;
    private  Usuarios usuario;
    private boolean aprobada;

    private boolean entregado;

    public ReservaImplemento(String documentId, Date fechaSolicitud, Date fechaReserva, Date fechaDevolucion,Implementos implemento, Usuarios usuario, boolean aprobada, boolean entregado) {
        this.documentId = documentId;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaReserva = fechaReserva;
        this.fechaDevolucion = fechaDevolucion;
        this.usuario = usuario;
        this.aprobada = aprobada;
        this.entregado = entregado;
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
}
