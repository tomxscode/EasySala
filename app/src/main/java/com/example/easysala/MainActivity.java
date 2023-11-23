package com.example.easysala;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easysala.models.Bloque;
import com.example.easysala.models.CallbackBloque;
import com.example.easysala.models.CallbackDia;
import com.example.easysala.models.CallbackHorario;
import com.example.easysala.models.CallbackImplemento;
import com.example.easysala.models.CallbackModelo;
import com.example.easysala.models.CallbackSala;
import com.example.easysala.models.CallbackTipoImplemento;
import com.example.easysala.models.CallbackTipoSala;
import com.example.easysala.models.CallbackTipoinmobiliario;
import com.example.easysala.models.CallbackUsuario;
import com.example.easysala.models.Dia;
import com.example.easysala.models.Horario;
import com.example.easysala.models.Implementos;
import com.example.easysala.models.Modelo;
import com.example.easysala.models.Salas;
import com.example.easysala.models.TipoImplemento;
import com.example.easysala.models.TipoInmobiliario;
import com.example.easysala.models.TipoSala;
import com.example.easysala.models.Usuarios;
import com.example.easysala.ui.home.HomeFragment;
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
                toast_ok("Sesión iniciada correctamente");
                
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

                                Horario modelo = new Horario("zUXo2twbeCqDF4OYNQXE");
                                modelo.obtenerInfo(new CallbackHorario(){
                                    @Override
                                    public void onObtenerInfo(boolean estado) {
                                        if (estado) {
                                            // Alert Dialog que muestre la información de la sala, horario y dia
                                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                            builder.setTitle("Información");
                                            builder.setMessage("Sala: " + modelo.getSalaHorario().getNombreSala() + "\n" +
                                                    "Hora Inicio: " + modelo.getBloqueHorario().getHoraInicio() + "\n" +
                                                    "Hora Fin: " + modelo.getBloqueHorario().getHoraFin() + "\n" +
                                                    "Dia: " + modelo.getDiaHorario().getNombre());
                                            builder.setPositiveButton("Aceptar", null);
                                            builder.show();

                                            //Toast.makeText(MainActivity.this, "Tipo de inmobiliario: " + modelo.getBloqueHorario().getHoraFin(), Toast.LENGTH_SHORT).show();
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
    public void toast_ok(String msg){
        LayoutInflater layoutInflater = getLayoutInflater();
        View v = layoutInflater.inflate(R.layout.custom_toast_ok, (ViewGroup) findViewById(R.id.ll_custom_toast_ok));
        TextView txtMensaje = v.findViewById(R.id.txtMensajeToastOk);
        txtMensaje.setText(msg);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0,200);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(v);
        toast.show();
    }

}