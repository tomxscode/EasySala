package com.example.easysala.models;

public class Dia {

    private int IdDia;
    private String nombre;

    public Dia(int idDia, String nombre) {
        IdDia = idDia;
        this.nombre = nombre;
    }

    public int getIdDia() {
        return IdDia;
    }

    public void setIdDia(int idDia) {
        IdDia = idDia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
