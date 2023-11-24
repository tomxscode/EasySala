package com.example.easysala.ui.dashboard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easysala.AprobarReservas;
import com.example.easysala.MainActivity;
import com.example.easysala.R;
import com.example.easysala.databinding.FragmentDashboardBinding;
import com.example.easysala.models.CallbackHorario;
import com.example.easysala.models.Horario;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    RecyclerView rv;
    private FragmentDashboardBinding binding;
    private List<Horario> elements;
    String disponible = "";

    Adapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        Button btn_aprobar = view.findViewById(R.id.btn_aprobar); // Reemplaza con el ID real de tu botón

        // Verifica el rol del usuario y establece la visibilidad del botón
        if (MainActivity.rolUsuario > 2) {
            btn_aprobar.setVisibility(View.VISIBLE);
            Log.d("MainActivity", "si aparece :) rol =" + MainActivity.rolUsuario);
        } else {
            btn_aprobar.setVisibility(View.GONE);
            Log.d("MainActivity", "no aparece :( rol = " + MainActivity.rolUsuario);
        }
        btn_aprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarFragmento(new AprobarReservas());
            }
        });
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void cargarFragmento(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null); // Opcional, para agregar a la pila de retroceso
        fragmentTransaction.commit();
    }

}