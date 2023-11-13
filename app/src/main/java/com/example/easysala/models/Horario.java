package com.example.easysala.models;

public class Horario {

    private int IdHorario;

    private boolean Disponibilidad;

    private Bloque BloqueHorario;

    private Dia DiaHorario;

    private Salas SalaHorario;



    public Horario(int idHorario, boolean disponibilidad, Bloque bloqueHorario, Dia diaHorario, Salas salaHorario) {
        IdHorario = idHorario;
        Disponibilidad = disponibilidad;
        BloqueHorario = bloqueHorario;
        DiaHorario = diaHorario;
        SalaHorario = salaHorario;
    }

    public Salas getSalaHorario() {
        return SalaHorario;
    }

    public void setSalaHorario(Salas salaHorario) {
        SalaHorario = salaHorario;
    }

    public int getIdHorario() {
        return IdHorario;
    }

    public void setIdHorario(int idHorario) {
        IdHorario = idHorario;
    }

    public boolean isDisponibilidad() {
        return Disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        Disponibilidad = disponibilidad;
    }

    public Bloque getBloqueHorario() {
        return BloqueHorario;
    }

    public void setBloqueHorario(Bloque bloqueHorario) {
        BloqueHorario = bloqueHorario;
    }

    public Dia getDiaHorario() {
        return DiaHorario;
    }

    public void setDiaHorario(Dia diaHorario) {
        DiaHorario = diaHorario;
    }
}
