package com.example.easysala.models;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Usuarios {

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

    public void obtenerInfo() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuario")
                .whereEqualTo("uid_user", uid_bd)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        for (DocumentSnapshot document : querySnapshot) {
                            documentId = document.getId();
                            nombre = document.getString("nombre");
                            apellido = document.getString("apellido");
                            rol = Integer.parseInt(document.getString("rol"));
                            correo = document.getString("correo");
                            habilitado = document.getBoolean("habilitado");
                        }
                    }
                });
    }

    public void guardarBd() {
        FirebaseFirestore  db = FirebaseFirestore.getInstance();
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

}
