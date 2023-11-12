package com.example.easysala.models;

public class TipoSala {

    private int IdTipoSala;
    private String NombreTipoSala;
    private String Descripcion;

    public TipoSala(int idTipoSala, String nombreTipoSala, String descripcion) {
        IdTipoSala = idTipoSala;
        NombreTipoSala = nombreTipoSala;
        Descripcion = descripcion;
    }

    public int getIdTipoSala() {
        return IdTipoSala;
    }

    public void setIdTipoSala(int idTipoSala) {
        IdTipoSala = idTipoSala;
    }

    public String getNombreTipoSala() {
        return NombreTipoSala;
    }

    public void setNombreTipoSala(String nombreTipoSala) {
        NombreTipoSala = nombreTipoSala;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
