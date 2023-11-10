package com.example.easysala.models;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Usuarios {

    private String documentId;
    private String nombre;
    private String apellido;
    private String correo;
    private int rol;
    private boolean sesionIniciada;
    private String uid_bd;

    public Usuarios(String documentId, String nombre, String apellido, String correo, int rol, boolean sesionIniciada) {
        this.documentId = documentId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.rol = rol;
        this.sesionIniciada = sesionIniciada;
    }

    public Usuarios(String documentId, String correo) {
        this.documentId = documentId;
        this.correo = correo;
    }

    public Usuarios(String nombre, String apellido, String correo, int rol, String documentId, String uid_bd) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.rol = rol;
        this.documentId = documentId;
        this.uid_bd  = uid_bd;
    }

    public void guardarBd() {
        FirebaseFirestore  db = FirebaseFirestore.getInstance();
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("nombre", nombre);
        usuario.put("apellido", apellido);
        usuario.put("correo", correo);
        usuario.put("rol", rol);
        usuario.put("uid_user", documentId);

        db.collection("usuario")
                .add(usuario)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        uid_bd = documentReference.getId();
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

    public boolean isSesionIniciada() {
        return sesionIniciada;
    }

    public void setSesionIniciada(boolean sesionIniciada) {
        this.sesionIniciada = sesionIniciada;
    }
}
