package com.example.easysala.models;

public interface CallbackReservaSala {
    void onError(String mensaje);
    void onInfoEncontrada(boolean estado);

    void onReservaRealizada(boolean estado);
}
