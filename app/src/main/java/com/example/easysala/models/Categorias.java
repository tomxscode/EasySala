package com.example.easysala.models;

public class Categorias {

    private String documentId;
    private String titulo;
    private String descripcion;

    public Categorias(String documentId, String titulo, String descripcion) {
        this.documentId = documentId;
        this.titulo = titulo;
        this.descripcion = descripcion;
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
}
