package com.example.easysala.models;

public class TipoImplemento {

    private int IdTipoImplemento;

    private String NombreTipoImplemento;

    private String Descripcion;

    public TipoImplemento(int idTipoImplemento, String nombreTipoImplemento, String descripcion) {
        IdTipoImplemento = idTipoImplemento;
        NombreTipoImplemento = nombreTipoImplemento;
        Descripcion = descripcion;
    }

    public int getIdTipoImplemento() {
        return IdTipoImplemento;
    }

    public void setIdTipoImplemento(int idTipoImplemento) {
        IdTipoImplemento = idTipoImplemento;
    }

    public String getNombreTipoImplemento() {
        return NombreTipoImplemento;
    }

    public void setNombreTipoImplemento(String nombreTipoImplemento) {
        NombreTipoImplemento = nombreTipoImplemento;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
