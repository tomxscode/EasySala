package com.example.easysala.models;

public class Modelo {

    private int IdModelo;
    private String NombreModelo;

    private Marca MarcaModelo;

    public Modelo(int idModelo, String nombreModelo, Marca marcaModelo) {
        IdModelo = idModelo;
        NombreModelo = nombreModelo;
        MarcaModelo = marcaModelo;
    }

    public int getIdModelo() {
        return IdModelo;
    }

    public void setIdModelo(int idModelo) {
        IdModelo = idModelo;
    }

    public String getNombreModelo() {
        return NombreModelo;
    }

    public void setNombreModelo(String nombreModelo) {
        NombreModelo = nombreModelo;
    }

    public Marca getMarcaModelo() {
        return MarcaModelo;
    }

    public void setMarcaModelo(Marca marcaModelo) {
        MarcaModelo = marcaModelo;
    }
}
