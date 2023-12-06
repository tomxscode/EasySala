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

import com.example.easysala.models.CallbackHorario;
import com.example.easysala.models.CallbackImplemento;
import com.example.easysala.models.CallbackUsuario;
import com.example.easysala.models.Horario;
import com.example.easysala.models.Implementos;
import com.example.easysala.models.Usuarios;
import com.example.easysala.ui.dashboard.DashboardFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.easysala.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static int rolUsuario;
    private FirebaseAuth mAuth;
    private ActivityMainBinding binding;

    public static Usuarios usuarioActual;
    public static boolean sesionIniciada = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = new DashboardFragment();
        fm.beginTransaction().add(R.id.nav_host_fragment_activity_main, fragment);




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
                                MainActivity.rolUsuario = MainActivity.usuarioActual.getRol();
                                sesionIniciada = true;
                                //Toast.makeText(MainActivity.this, "Bienvenido " + usuarioActual.getNombre(), Toast.LENGTH_SHORT).show()

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