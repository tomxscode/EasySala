package com.example.easysala;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easysala.models.CallbackUsuario;
import com.example.easysala.models.Usuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    String email, password;
    Button btnLogin;
    Button btnRegistrar;
    TextInputEditText editText, editTextpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Iniciar sesión");
        //Oculta la barra que aparece arriba al iniciar la aplicacion
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // Integración de firebase Auth
        mAuth = FirebaseAuth.getInstance();

        TextInputLayout textInputLayout = findViewById(R.id.txt_login_correo);
        editText = (TextInputEditText) textInputLayout.getEditText();
        TextInputLayout textInputLayoutpass = findViewById(R.id.txt_login_pass);
        editTextpass = (TextInputEditText) textInputLayoutpass.getEditText();
        password = editTextpass.getText().toString();
        btnRegistrar = findViewById(R.id.btn_login_reg);
        btnLogin = findViewById(R.id.btn_login_ini);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                email = editText.getText().toString();
                password = editTextpass.getText().toString();

                String emailTxt = email;
                String passTxt = password;
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
                            ProgressDialog dialogCargando = ProgressDialog.show(LoginActivity.this, "Espere...", "Estamos verificando la información");

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Inicio de sesión exitoso

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    // uid del usuario
                                    String uid = user.getUid();
                                    MainActivity.usuarioActual = new Usuarios(uid);
                                    MainActivity.usuarioActual.obtenerInfo(new CallbackUsuario() {
                                        @Override
                                        public void onError(String mensaje) {

                                        }

                                        @Override
                                        public void onObtenerInfo(boolean encontrado) {
                                            if (encontrado) {
                                                dialogCargando.dismiss();
                                                if (MainActivity.usuarioActual.isHabilitado()) {
                                                    MainActivity.sesionIniciada = true;
                                                    Intent pagPrincipal = new Intent(LoginActivity.this, MainActivity.class);
                                                    startActivity(pagPrincipal);
                                                } else {
                                                    mAuth.signOut();
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                                    builder.setTitle("Información");
                                                    builder.setMessage("Tu cuenta está deshabilitada, por favor contacta con el administrador");
                                                    builder.setPositiveButton("Aceptar", null);
                                                    builder.show();
                                                }
                                            }
                                        }
                                    });
                                } else {
                                    dialogCargando.dismiss();
                                    Toast.makeText(LoginActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailTxt = email;
                String passTxt = password;
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