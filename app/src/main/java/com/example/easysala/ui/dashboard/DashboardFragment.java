package com.example.easysala.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easysala.ListAdapter;
import com.example.easysala.ListElement;
import com.example.easysala.R;
import com.example.easysala.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private List<ListElement> elements;
    private ListAdapter listAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Inicializa datos
        elements = new ArrayList<>();
        elements.add(new ListElement("#0C7AE8", "Laboratorio", "317", "Disponible" ));
        elements.add(new ListElement("#0CE845", "Sala", "109", "Ocupada" ));
        elements.add(new ListElement("#E8870C", "Auditorio", "Entrada", "Disponible" ));
        elements.add(new ListElement("#0C7AE8", "Laboratorio", "318", "Ocupada" ));

        // Crea adaptador
        listAdapter = new ListAdapter(elements, getActivity());

        // Obtén RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.ListRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(listAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}