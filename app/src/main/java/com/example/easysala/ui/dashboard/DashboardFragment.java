package com.example.easysala.ui.dashboard;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.easysala.AprobarReservas;
import com.example.easysala.MainActivity;
import com.example.easysala.R;
import com.example.easysala.aprobar_reservas_impl;
import com.example.easysala.databinding.FragmentDashboardBinding;
import com.example.easysala.models.Horario;

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

        Button btn_aprobar_salas = view.findViewById(R.id.btn_aprobar_salas);
        Button btn_aprobar_implementos = view.findViewById(R.id.btn_aprobar_implementos);
        if (MainActivity.rolUsuario > 2) {
            btn_aprobar_salas.setVisibility(View.VISIBLE);
            btn_aprobar_implementos.setVisibility(View.VISIBLE);
        } else {
            btn_aprobar_salas.setVisibility(View.GONE);
            btn_aprobar_implementos.setVisibility(View.GONE);
        }
        btn_aprobar_salas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarFragmento(new AprobarReservas());
            }
        });
        btn_aprobar_implementos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarFragmento(new aprobar_reservas_impl());
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
        fragmentTransaction.commit();
    }

}