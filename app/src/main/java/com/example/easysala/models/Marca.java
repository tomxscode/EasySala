package com.example.easysala.models;

public class Marca {

    private int IdMarca;
    private String NombreMarca;

    public Marca(int idMarca, String nombreMarca) {
        IdMarca = idMarca;
        NombreMarca = nombreMarca;
    }

    public int getIdMarca() {
        return IdMarca;
    }

    public void setIdMarca(int idMarca) {
        IdMarca = idMarca;
    }

    public String getNombreMarca() {
        return NombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        NombreMarca = nombreMarca;
    }
}
