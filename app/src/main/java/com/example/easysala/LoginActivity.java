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
                                    Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                                    Intent pagPrincipal = new Intent(LoginActivity.this, MainActivity.class);
                                    // Creación del objeto Usuario con los datos actuales
                                    MainActivity.usuarioActual = new Usuarios(user.getUid(), user.getEmail());

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
                mAuth.createUserWithEmailAndPassword(emailTxt, passTxt);
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