package com.example.easysala.models;

public class TiposSalas {
    private String documentId;
    private String titulo;
    private String descripcion;
    private int permiso;

    public TiposSalas(String documentId, String titulo, String descripcion, int permiso) {
        this.documentId = documentId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.permiso = permiso;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPermiso() {
        return permiso;
    }

    public void setPermiso(int permiso) {
        this.permiso = permiso;
    }
}
