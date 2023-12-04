package com.example.easysala;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.easysala.databinding.FragmentHomeBinding;
import com.example.easysala.models.Implementos;
import com.example.easysala.models.Modelo;
import com.example.easysala.models.ReservaImplemento;
import com.example.easysala.models.TipoImplemento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link aprobar_reservas_impl#newInstance} factory method to
 * create an instance of this fragment.
 */
public class aprobar_reservas_impl extends Fragment {

    private ListView listReservasImpl;
    private FragmentHomeBinding binding;
    private ArrayList<String> listaNomReservasImpl;
    private ArrayList<Implementos> implementosInfo;
    private Spinner spinnerMarcas;

    HashMap<String, String> mapeoMarcas = new HashMap<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public aprobar_reservas_impl() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment aprobar_reservas_impl.
     */
    // TODO: Rename and change types and number of parameters
    public static aprobar_reservas_impl newInstance(String param1, String param2) {
        aprobar_reservas_impl fragment = new aprobar_reservas_impl();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_aprobar_reservas_impl, container, false);
        listReservasImpl = view.findViewById(R.id.list_reservas_aprobar_impl);
        listaNomReservasImpl = new ArrayList<>();
        implementosInfo = new ArrayList<>();
        spinnerMarcas = view.findViewById(R.id.spinner_modelo);
        poblarSpinnerMarcas();

        spinnerMarcas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String marcaSeleccionada = spinnerMarcas.getSelectedItem().toString();
                if (marcaSeleccionada.equals("Todas las marcas")) {
                    retornarListaImplementosFirebase();
                } else {
                    String idMarca = mapeoMarcas.get(marcaSeleccionada);
                    retornarListaImplementosFirebase();
                    Toast.makeText(requireContext(), "Marca seleccionada: " + idMarca, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }
    public void limpiarListaImplementos() {
        listaNomReservasImpl.clear();
        implementosInfo.clear();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, listaNomReservasImpl);
        listReservasImpl.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public void retornarListaImplementosFirebase() {
        limpiarListaImplementos();
        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Cargando implementos...");
        progressDialog.setMessage("Espere unos segundos...");
        progressDialog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("reserva_implemento").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                // Mensaje de cargando

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documento : task.getResult()) {
                        Boolean aprobada = documento.getBoolean("aprobada");
                        Boolean entregado = documento.getBoolean("entregado");
                        Date fecha_devolucion = documento.getDate("fecha_devolucion");
                        Date fecha_reserva = documento.getDate("fecha_reserva");
                        Date fecha_solicitud = documento.getDate("fecha_solucitud");
                        String implemento = documento.getString("implemento");
                        String usuario = documento.getString("usuario");


                        String fechaFormateada = formatearFechaResumida(fecha_reserva);

                        ReservaImplemento nuevaReservaImplemento = new ReservaImplemento(fecha_solicitud, fecha_reserva, fecha_devolucion, null, null, aprobada, entregado);
                        db.collection("implemento").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                // Mensaje de cargando

                                String nombreImplementoReserva = null;
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot documento : task.getResult()) {
                                        String idImplemento = documento.getId();
                                        String nombreImplemento = documento.getString("nombre");
                                        if (idImplemento.equals(implemento)) {
                                            nombreImplementoReserva = nombreImplemento;
                                        }
                                    }

                                    listaNomReservasImpl.add("Estado: " + aprobada + " " + nombreImplementoReserva + " " + fechaFormateada);

                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, listaNomReservasImpl);
                                    listReservasImpl.setAdapter(adapter);
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private String formatearFechaResumida(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        return formato.format(fecha);
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
        }