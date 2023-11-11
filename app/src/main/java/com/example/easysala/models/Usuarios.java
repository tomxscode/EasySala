package com.example.easysala.models;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayoutStates;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Usuarios implements CallbackUsuario {

    private String documentId; // documentId de la colección usuarios
    private String nombre;
    private String apellido;
    private String correo;
    private int rol;
    private String uid_bd; // uid del usuario en authentication
    private boolean habilitado;

    public Usuarios(String uid_bd) {
        this.uid_bd = uid_bd;
    }

    public Usuarios(String nombre, String apellido, String correo, int rol, String uid_bd, boolean habilitado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.rol = rol;
        this.uid_bd = uid_bd;
        this.habilitado = habilitado;
    }

    public String getUid_bd() {
        return uid_bd;
    }

    public void setUid_bd(String uid_bd) {
        this.uid_bd = uid_bd;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    /*public void obtenerInfo(CallbackUsuario callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuario")
                .whereEqualTo("uid_user", uid_bd)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document =  task.getResult().getDocuments().get(0);
                            if (document.exists()) {
                                // Asignación a los atributos
                                documentId = document.getId();
                                nombre = document.getString("nombre");
                                apellido = document.getString("apellido");
                                rol = document.getLong("rol").intValue();
                                correo = document.getString("correo");
                                habilitado = document.getBoolean("habilitado");
                                Log.d(ConstraintLayoutStates.TAG, "Documento encontrado");
                                Log.d(ConstraintLayoutStates.TAG, nombre);
                            } else {
                                Log.d(ConstraintLayoutStates.TAG, "Documento no encontrado");
                            }
                        }
                    }
                });
    }*/

    public void obtenerInfo(CallbackUsuario callback) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("usuario")
                .whereEqualTo("uid_user", uid_bd)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            DocumentSnapshot document = task.getResult().getDocuments().get(0);

                            if (document.exists()) {

                                nombre = document.getString("nombre");
                                apellido = document.getString("apellido");
                                correo = document.getString("correo");
                                rol = document.getLong("rol").intValue();
                                habilitado = document.getBoolean("habilitado");
                                documentId = document.getId();
                                Log.d(ConstraintLayoutStates.TAG, "Documento encontrado");

                                callback.onObtenerInfo(true);

                            } else {

                                callback.onObtenerInfo(false);

                            }

                        } else {

                            callback.onError("Error en consulta");

                        }

                    }
                });

    }


    public void guardarBd() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("nombre", nombre);
        usuario.put("apellido", apellido);
        usuario.put("correo", correo);
        usuario.put("rol", rol);
        usuario.put("uid_user", uid_bd);
        usuario.put("habilitado", habilitado);

        db.collection("usuario")
                .add(usuario)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        documentId = documentReference.getId();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("error");
                    }
                });
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    @Override
    public void onError(String mensaje) {

    }

    @Override
    public void onObtenerInfo(boolean encontrado) {

    }
}
