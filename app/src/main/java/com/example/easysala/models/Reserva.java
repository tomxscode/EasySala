package com.example.easysala.models;

public class Reserva {
    private int IdReserva;

    private String Fecha;
    private String HoraInicio;
    private String HoraFin;
    private String Descripcion;

    public Reserva(int idReserva, String fecha, String horaInicio, String horaFin, String descripcion) {
        IdReserva = idReserva;
        Fecha = fecha;
        HoraInicio = horaInicio;
        HoraFin = horaFin;
        Descripcion = descripcion;
    }

    public int getIdReserva() {
        return IdReserva;
    }

    public void setIdReserva(int idReserva) {
        IdReserva = idReserva;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(String horaInicio) {
        HoraInicio = horaInicio;
    }

    public String getHoraFin() {
        return HoraFin;
    }

    public void setHoraFin(String horaFin) {
        HoraFin = horaFin;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
