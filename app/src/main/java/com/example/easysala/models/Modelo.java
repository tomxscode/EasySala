package com.example.easysala.models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayoutStates;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Modelo implements CallbackModelo {

    private String IdModelo;
    private String NombreModelo;

    private Marca MarcaModelo;

    public Modelo(String idModelo, String nombreModelo, Marca marcaModelo) {
        IdModelo = idModelo;
        NombreModelo = nombreModelo;
        MarcaModelo = marcaModelo;
    }

    public Modelo() {
    }

    public Modelo(String idModelo) {
        IdModelo = idModelo;
    }

    public String getIdModelo() {
        return IdModelo;
    }

    public void setIdModelo(String idModelo) {
        IdModelo = idModelo;
    }

    public String getNombreModelo() {
        return NombreModelo;
    }

    public void setNombreModelo(String nombreModelo) {
        NombreModelo = nombreModelo;
    }

    public Marca getMarcaModelo() {
        return MarcaModelo;
    }

    public void setMarcaModelo(Marca marcaModelo) {
        MarcaModelo = marcaModelo;
    }

    public void obtenerCantidadModelos() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("modelo_implemento")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("CANTIDAD MODELOS", String.valueOf(task.getResult().size()));
                        }
                    }
                });
    }

    public void obtenerInfo(CallbackModelo callback) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("modelo_implemento").document(this.getIdModelo());
        docRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {

                            DocumentSnapshot document = task.getResult();

                            if (document.exists()) {
                                setNombreModelo(document.getString("nombre"));
                                String documentMarca = document.getString("marca");
                                //.
                                Marca marca = new Marca(documentMarca);
                                marca.obtenerInfo(new CallbackMarca() {
                                    @Override
                                    public void onError(String error) {

                                    }

                                    @Override
                                    public void onObtenerInfo(boolean encontrado) {
                                        if (encontrado) {
                                            setMarcaModelo(marca);
                                            callback.onInfoCargada(true);
                                        } else {
                                            setMarcaModelo(null);
                                            callback.onInfoCargada(false);
                                        }
                                    }
                                });
                            } else {
                                callback.onInfoCargada(false);
                            }

                        } else {
                            callback.onError("Error en consulta");
                        }

                    }
                });

    }

    @Override
    public void onError(String mensaje) {

    }

    @Override
    public void onInfoCargada(boolean estado) {

    }
}
