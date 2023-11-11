package com.example.easysala;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.easysala.models.CallbackUsuario;
import com.example.easysala.models.Usuarios;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
            Toast.makeText(this, "Bienvenido " + user.getEmail(), Toast.LENGTH_SHORT).show();
            MainActivity.usuarioActual = new Usuarios(user.getUid());
            MainActivity.usuarioActual.obtenerInfo(new CallbackUsuario() {
                @Override
                public void onError(String mensaje) {

                }

                @Override
                public void onObtenerInfo(boolean encontrado) {
                    if (encontrado) {
                        if (!MainActivity.usuarioActual.isHabilitado()) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(MainActivity.this, "Usuario inhabilitado", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            });
        } else {
            Intent  intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }

}