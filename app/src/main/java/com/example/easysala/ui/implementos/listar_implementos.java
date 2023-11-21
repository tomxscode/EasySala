package com.example.easysala.ui.implementos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.easysala.R;
import com.example.easysala.models.CallbackMarca;
import com.example.easysala.models.CallbackTipoImplemento;
import com.example.easysala.models.Implementos;
import com.example.easysala.models.Marca;
import com.example.easysala.models.Modelo;
import com.example.easysala.models.TipoImplemento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class listar_implementos extends Fragment {

    private ListView listViewImplementos;
    private ArrayList<String> listaImplementos;
    private ArrayList<Implementos> implementosInfo;
    private Spinner spinnerMarcas;

    HashMap<String, String> mapeoMarcas = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar_implementos, container, false);
        listViewImplementos = view.findViewById(R.id.list_implementos);
        listaImplementos = new ArrayList<>();
        implementosInfo = new ArrayList<>();
        spinnerMarcas = view.findViewById(R.id.spinner_modelo);
        //retornarListaUsuariosFirebase();
        poblarSpinnerMarcas();

        spinnerMarcas.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String marcaSeleccionada = spinnerMarcas.getSelectedItem().toString();
                if (marcaSeleccionada.equals("Todas las marcas")) {
                    retornarListaUsuariosFirebase();
                } else {
                    String idMarca = mapeoMarcas.get(marcaSeleccionada);
                    retornarListaUsuariosFirebase();
                    Toast.makeText(requireContext(), "Marca seleccionada: " + idMarca, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    public void poblarSpinnerMarcas() {
        // HashMap para mapear nombre - ID
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("marca_implemento").get().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                List<String> marcas;
                marcas = new ArrayList<>();
                marcas.add("Todas las marcas");

                for (DocumentSnapshot doc : task.getResult()) {

                    String nombreMarca = doc.getString("nombre");
                    String idMarca = doc.getId();

                    marcas.add(nombreMarca);

                    mapeoMarcas.put(nombreMarca, idMarca);

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                        android.R.layout.simple_spinner_item, marcas);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinnerMarcas.setAdapter(adapter);

            } else {

                // Manejar error
                AlertDialog alertDialog = new AlertDialog.Builder(requireContext()).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("No se pudieron obtener las marcas." +
                        "Por lo que se desactivó el filtro de marcas, aún así, podrás usar el resto de funcionalidades" +
                        " Lo sentimos.");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar", (dialog, which) -> {
                    dialog.dismiss();
                    spinnerMarcas.setEnabled(false);
                    List<String> marcaError = new ArrayList<>();
                    marcaError.add("Filtro desactivado");
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, marcaError);
                    spinnerMarcas.setAdapter(adapter);
                });
                alertDialog.show();
            }

        });

    }

    public void limpiarListaImplementos() {
        listaImplementos.clear();
        implementosInfo.clear();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, listaImplementos);
        listViewImplementos.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void retornarListaUsuariosFirebase() {
        limpiarListaImplementos();
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
                        Implementos nuevoImplemento = new Implementos(idImplemento, nombre, ubicacion, cantidad, descripcion, new Modelo(modelo), new TipoImplemento(tipo));
                        implementosInfo.add(nuevoImplemento);
                        listaImplementos.add(nombre + ". Disponibles " + cantidad + " unidades");
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, listaImplementos);
                    listViewImplementos.setAdapter(adapter);
                    listViewImplementos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            AlertDialog alertDialog = new AlertDialog.Builder(requireContext()).create();
                            Implementos implementoActual = implementosInfo.get(position);
                            alertDialog.setTitle(implementoActual.getNombreImplemento());
                            alertDialog.setMessage("Información del implemento:\n" +
                                    "\tDisponibles: " + implementoActual.getCantidad() + "\n" +
                                    "\tUbicación: " + implementoActual.getUbicacion() + "\n" +
                                    "\t'" + implementoActual.getDescripcion() + "'\n" +
                                    "** Otra información relevante **\n" +
                                    "\tMarca y modelo: " + implementoActual.getModeloImplemento().getMarcaModelo() + " " + implementoActual.getModeloImplemento() + "\n" +
                                    "\tTipo: " + implementoActual.getTipoImplemento());
                            // Botón de aceptar, con texto: Reservar
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Reservar", (dialog, which) -> {
                                dialog.dismiss();
                                AlertDialog alertDialog2 = new AlertDialog.Builder(requireContext()).create();
                                alertDialog2.setTitle("Reserva");
                                alertDialog2.setMessage("Implemento reservado");
                                alertDialog2.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar", (dialog2, which2) -> {
                                    dialog2.dismiss();
                                });
                            });

                            alertDialog.show();
                            Toast.makeText(requireContext(), "Implemento seleccionado " + position, Toast.LENGTH_SHORT).show();
                        }
                    });
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
