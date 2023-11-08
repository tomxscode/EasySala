package com.example.easysala;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email, password;
    Button btnLogin;
    Button btnRegistrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Integración de firebase Auth
         mAuth = FirebaseAuth.getInstance();

         email = findViewById(R.id.txt_login_correo);
         password  = findViewById(R.id.txt_login_pass);
         btnRegistrar = findViewById(R.id.btn_login_reg);
         btnRegistrar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                String emailTxt = email.getText().toString();
                String passTxt = password.getText().toString();
                registrarUsuario(emailTxt, passTxt);
             }
         });
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
        // Comprobar que el usuario se creó con éxito
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            return false; // Si el usuario no se creó, devolver false y no crear el usuario
        }
        return true;
    }
}