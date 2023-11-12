package com.example.easysala.models;

public class Implementos {

    private int IdImplemento;
    private String NombreImplemento;
    private String Ubicacion;
    private int Cantidad;
    private String Descripcion;

    private Modelo ModeloImplemento;

    private TipoImplemento TipoImplemento;

    public Implementos(int idImplemento, String nombreImplemento, String ubicacion, int cantidad, String descripcion, Modelo modeloImplemento, com.example.easysala.models.TipoImplemento tipoImplemento) {
        IdImplemento = idImplemento;
        NombreImplemento = nombreImplemento;
        Ubicacion = ubicacion;
        Cantidad = cantidad;
        Descripcion = descripcion;
        ModeloImplemento = modeloImplemento;
        TipoImplemento = tipoImplemento;
    }

    public int getIdImplemento() {
        return IdImplemento;
    }

    public void setIdImplemento(int idImplemento) {
        IdImplemento = idImplemento;
    }

    public String getNombreImplemento() {
        return NombreImplemento;
    }

    public void setNombreImplemento(String nombreImplemento) {
        NombreImplemento = nombreImplemento;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Modelo getModeloImplemento() {
        return ModeloImplemento;
    }

    public void setModeloImplemento(Modelo modeloImplemento) {
        ModeloImplemento = modeloImplemento;
    }

    public com.example.easysala.models.TipoImplemento getTipoImplemento() {
        return TipoImplemento;
    }

    public void setTipoImplemento(com.example.easysala.models.TipoImplemento tipoImplemento) {
        TipoImplemento = tipoImplemento;
    }
}
