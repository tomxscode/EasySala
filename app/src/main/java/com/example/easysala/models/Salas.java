package com.example.easysala.models;

public class Salas {

    private int IdSala;
    private String NombreSala;
    private int Capacidad;
    private String Descripcion;

    private TipoInmobiliario TipoInmobiliarioSala;

    private TipoSala TipoSala;

    public Salas(int idSala, String nombreSala, int capacidad, String descripcion, TipoInmobiliario tipoInmobiliarioSala, com.example.easysala.models.TipoSala tipoSala) {
        IdSala = idSala;
        NombreSala = nombreSala;
        Capacidad = capacidad;
        Descripcion = descripcion;
        TipoInmobiliarioSala = tipoInmobiliarioSala;
        TipoSala = tipoSala;
    }

    public TipoInmobiliario getTipoInmobiliarioSala() {
        return TipoInmobiliarioSala;
    }

    public void setTipoInmobiliarioSala(TipoInmobiliario tipoInmobiliarioSala) {
        TipoInmobiliarioSala = tipoInmobiliarioSala;
    }

    public com.example.easysala.models.TipoSala getTipoSala() {
        return TipoSala;
    }

    public void setTipoSala(com.example.easysala.models.TipoSala tipoSala) {
        TipoSala = tipoSala;
    }

    public int getIdSala() {
        return IdSala;
    }

    public void setIdSala(int idSala) {
        IdSala = idSala;
    }

    public String getNombreSala() {
        return NombreSala;
    }

    public void setNombreSala(String nombreSala) {
        NombreSala = nombreSala;
    }

    public int getCapacidad() {
        return Capacidad;
    }

    public void setCapacidad(int capacidad) {
        Capacidad = capacidad;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
