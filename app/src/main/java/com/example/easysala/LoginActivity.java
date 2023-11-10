package com.example.easysala;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easysala.models.Usuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
        setTitle("Iniciar sesión");

        // Integración de firebase Auth
         mAuth = FirebaseAuth.getInstance();

         email = findViewById(R.id.txt_login_correo);
         password  = findViewById(R.id.txt_login_pass);
         btnRegistrar = findViewById(R.id.btn_login_reg);
         btnLogin = findViewById(R.id.btn_login_ini);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {;
                String emailTxt = email.getText().toString();
                String passTxt = password.getText().toString();
                // Comprobación que los campos no estén vacíos
                if (emailTxt.isEmpty() || passTxt.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Comprobar que el dominio sea "@virginiogomez.cl"
                if (!emailTxt.endsWith("@virginiogomez.cl")) {
                    Toast.makeText(LoginActivity.this, "Tu correo no es de la institución", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(emailTxt, passTxt)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Inicio de sesión exitoso
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent pagPrincipal = new Intent(LoginActivity.this, MainActivity.class);

                                    MainActivity.usuarioActual = new Usuarios(user.getUid());
                                    MainActivity.usuarioActual.obtenerInfo();

                                    Toast.makeText(LoginActivity.this, "Bienvenido " + MainActivity.usuarioActual.getNombre(), Toast.LENGTH_SHORT).show();

                                    startActivity(pagPrincipal);
                                } else {
                                    // Error en el inicio de sesión
                                    Toast.makeText(LoginActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String emailTxt = email.getText().toString();
                 String passTxt = password.getText().toString();
                 String nombre = emailTxt.substring(0, emailTxt.indexOf("."));
                 String apellido = emailTxt.substring(emailTxt.indexOf(".") + 1, emailTxt.indexOf("@"));

                 mAuth.createUserWithEmailAndPassword(emailTxt, passTxt)
                         .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                             @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {
                                 if (task.isSuccessful()) {
                                     // Creación de usuario exitosa
                                     Toast.makeText(LoginActivity.this, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show();

                                     // Crear el objeto Usuarios
                                     MainActivity.usuarioActual = new Usuarios(nombre, apellido, emailTxt, 1, mAuth.getUid(), false);
                                     // Agregar a la BD
                                     MainActivity.usuarioActual.guardarBd();

                                     // Iniciar la actividad principal (MainActivity)
                                     Intent pagPrincipal = new Intent(LoginActivity.this, MainActivity.class);
                                     startActivity(pagPrincipal);
                                     finish();
                                 } else {
                                     // Error en la creación del usuario
                                     Toast.makeText(LoginActivity.this, "Error al crear el usuario", Toast.LENGTH_SHORT).show();
                                 }
                             }
                         });
             }
         });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Verifica si el usuario inició sesión
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


}