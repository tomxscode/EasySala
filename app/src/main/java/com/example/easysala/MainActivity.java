package com.example.easysala;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.easysala.models.CallbackModelo;
import com.example.easysala.models.CallbackTipoImplemento;
import com.example.easysala.models.CallbackUsuario;
import com.example.easysala.models.Modelo;
import com.example.easysala.models.TipoImplemento;
import com.example.easysala.models.Usuarios;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.easysala.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ActivityMainBinding binding;

    public static Usuarios usuarioActual;
    public static boolean sesionIniciada = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user  = mAuth.getCurrentUser();

        // Usuario actual
        if (user != null) {
            if (sesionIniciada) {
                Toast.makeText(this, "Sesión iniciada", Toast.LENGTH_SHORT).show();
                
            } else {
                usuarioActual = new Usuarios(user.getUid());
                ProgressDialog dialogoCargando = new ProgressDialog(this);
                dialogoCargando.setMessage("Cargando información...");
                dialogoCargando.show();
                //MainActivity.usuarioActual = new Usuarios(user.getUid());
                MainActivity.usuarioActual.obtenerInfo(new CallbackUsuario() {
                    @Override
                    public void onError(String mensaje) {

                    }

                    @Override
                    public void onObtenerInfo(boolean encontrado) {
                        if (encontrado) {
                            dialogoCargando.dismiss();
                            if (usuarioActual.isHabilitado()) {
                                sesionIniciada = true;
                                //Toast.makeText(MainActivity.this, "Bienvenido " + usuarioActual.getNombre(), Toast.LENGTH_SHORT).show();

                                TipoImplemento modelo = new TipoImplemento("T6P4TgV9jLwEnZTDKt7P");
                                modelo.obtenerInfo(new CallbackTipoImplemento() {
                                    @Override
                                    public void onInfoCargada(boolean estado) {
                                        if (estado) {
                                            Toast.makeText(MainActivity.this, "Modelo cargado " + modelo.getNombreTipoImplemento(), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(MainActivity.this, "No encontrado", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onError(String mensaje) {
                                        System.out.println(mensaje);
                                        Log.d("ERROR", mensaje);
                                    }
                                });

                            } else {
                                AlertDialog .Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Información");
                                builder.setMessage("Tu cuenta está deshabilitada, por favor contacta con el administrador");
                                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        sesionIniciada = false;
                                        usuarioActual = null;
                                        mAuth.signOut();
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                builder.show();
                            }
                        }
                    }
                });
            }
        } else {
            Intent  intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }

}