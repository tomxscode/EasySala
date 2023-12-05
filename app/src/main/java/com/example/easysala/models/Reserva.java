package com.example.easysala.models;

public class Reserva {
    private String documentId;
    private boolean aprobada;

    private Horario horario;

    public Reserva(String documentId, boolean aprobada, Horario horario) {
        this.documentId = documentId;
        this.aprobada = aprobada;
        this.horario = horario;
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
}
