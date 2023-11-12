package com.example.easysala.models;

public class TipoInmobiliario {

    private int IdTipoInmobiliario;
    private String NombreTipoInmobiliario;
    private String Descripcion;

    public TipoInmobiliario(int idTipoInmobiliario, String nombreTipoInmobiliario, String descripcion) {
        IdTipoInmobiliario = idTipoInmobiliario;
        NombreTipoInmobiliario = nombreTipoInmobiliario;
        Descripcion = descripcion;
    }

    public int getIdTipoInmobiliario() {
        return IdTipoInmobiliario;
    }

    public void setIdTipoInmobiliario(int idTipoInmobiliario) {
        IdTipoInmobiliario = idTipoInmobiliario;
    }

    public String getNombreTipoInmobiliario() {
        return NombreTipoInmobiliario;
    }

    public void setNombreTipoInmobiliario(String nombreTipoInmobiliario) {
        NombreTipoInmobiliario = nombreTipoInmobiliario;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
