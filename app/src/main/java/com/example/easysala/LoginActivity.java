package com.example.easysala;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Integración de firebase Auth
         mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Verifica si el usuario inició sesión
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public boolean registrarUsuario(String email, String password) {
        // Crear un usuario en Firebase y verificar que se haya creado exitosamente, caso contrario, mostrará un Toast de error
        if (email.isEmpty() || password.isEmpty()) {
            return false; // Si alguno de los campos está vacío, devolver false y no crear el usuario
        }

        // Comprobar que el email sea válido
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false; // Si el email no es válido, devolver false y no crear el usuario
        }


        mAuth.createUserWithEmailAndPassword(email, password);

        return true;
    }
}