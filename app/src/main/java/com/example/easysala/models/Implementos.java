package com.example.easysala.models;

public class Implementos {

    private String documentId;
    private String titulo;
    private String descripcion;
    private Categorias categoria;
    private int cantidad;

    public Implementos(String documentId, String titulo, String descripcion, Categorias categoria, int cantidad) {
        this.documentId = documentId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.cantidad = cantidad;
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

    public Categorias getCategoria() {
        return categoria;
    }

    public void setCategoria(Categorias categoria) {
        this.categoria = categoria;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
