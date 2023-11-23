package com.example.easysala;

public class ListElement {

    public String color;
    public String tipo;
    public String edificio;
    public String disponibilidad;

    public ListElement(String color, String nombre, String edificio, String estado) {
        this.color = color;
        this.tipo = nombre;
        this.edificio = edificio;
        this.disponibilidad = estado;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTipo() {
        return tipo;
    }

    public void setNombre(String nombre) {
        this.tipo = nombre;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public String getEstado() {
        return disponibilidad;
    }

    public void setEstado(String estado) {
        this.disponibilidad = estado;
    }
}
