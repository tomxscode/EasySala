package com.example.easysala.models;

public class Bloque {

    private int IdBloque;
    private String HoraInicio;
    private String HoraFin;

    public Bloque(int idBloque, String horaInicio, String horaFin) {
        IdBloque = idBloque;
        HoraInicio = horaInicio;
        HoraFin = horaFin;
    }

    public int getIdBloque() {
        return IdBloque;
    }

    public void setIdBloque(int idBloque) {
        IdBloque = idBloque;
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
}
