package com.example.easysala.models;

public class Salas {

    private String documentId;
    private String edificio;
    private TiposSalas tipo;
    private int capacidad;
    private String descripcion;

    public Salas(String documentId, String edificio, TiposSalas tipo, int capacidad, String descripcion) {
        this.documentId = documentId;
        this.edificio = edificio;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.descripcion = descripcion;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public TiposSalas getTipo() {
        return tipo;
    }

    public void setTipo(TiposSalas tipo) {
        this.tipo = tipo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
