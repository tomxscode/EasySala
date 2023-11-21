package com.example.easysala.ui.implementos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.easysala.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class listar_implementos extends Fragment {

    private ListView listViewImplementos;
    private ArrayList<String> listaImplementos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar_implementos, container, false);
        listViewImplementos = view.findViewById(R.id.list_implementos);
        listaImplementos = new ArrayList<>();
        retornarListaUsuariosFirebase();
        return view;
    }

    public void retornarListaUsuariosFirebase() {
        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Cargando implementos...");
        progressDialog.setMessage("Espere unos segundos...");
        progressDialog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("implemento").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                // Mensaje de cargando

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documento : task.getResult()) {
                        String idImplemento = documento.getId();
                        int cantidad = Integer.parseInt(documento.get("cantidad").toString());
                        String descripcion = documento.getData().get("descripcion").toString();
                        String modelo = documento.getData().get("modelo").toString();
                        String nombre = documento.getData().get("nombre").toString();
                        String tipo = documento.getData().get("tipo").toString();
                        String ubicacion = documento.getData().get("ubicacion").toString();

                        listaImplementos.add(nombre + " -> " + cantidad + " " + modelo);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, listaImplementos);
                    listViewImplementos.setAdapter(adapter);
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    // Alert Dialog informando que no se obtuvieron implementos
                    AlertDialog alertDialog = new AlertDialog.Builder(requireContext()).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("No se pudieron obtener los implementos");
                    // Botón de aceptar que te lleva al MainActivity
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar", (dialog, which) -> {
                        dialog.dismiss();
                        getActivity().getSupportFragmentManager().popBackStack();
                    });
                    alertDialog.show();

                }
            }
        });
    }
}
