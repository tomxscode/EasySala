package com.example.easysala.models;

public interface CallbackReservaImplemento {
    void onError(String mensaje);
    void onInfoEncontrada(boolean estado);

    void onReservaRealizada(boolean estado);
}
