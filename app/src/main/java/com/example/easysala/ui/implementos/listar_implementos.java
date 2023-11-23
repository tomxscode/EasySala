package com.example.easysala.ui.implementos;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.easysala.MainActivity;
import com.example.easysala.R;
import com.example.easysala.models.CallbackMarca;
import com.example.easysala.models.CallbackReservaImplemento;
import com.example.easysala.models.CallbackTipoImplemento;
import com.example.easysala.models.Implementos;
import com.example.easysala.models.Marca;
import com.example.easysala.models.Modelo;
import com.example.easysala.models.ReservaImplemento;
import com.example.easysala.models.TipoImplemento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
                                    "\t'" + implementoActual.getDescripcion() + "'\n");
                            // Botón de aceptar, con texto: Reservar
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Reservar", (dialog, which) -> {
                                dialog.dismiss();
                                AlertDialog  alertDialog1 = new AlertDialog.Builder(requireContext()).create();
                                alertDialog1.setTitle("Seleccionar fecha de reserva");
                                alertDialog1.setMessage("Por favor, seleccione una fecha de reserva en la siguiente pantalla.");
                                alertDialog1.setButton(AlertDialog.BUTTON_NEUTRAL, "Ir a seleccionar", (dialog1, which1) -> {
                                    Calendar calendario = Calendar.getInstance();
                                    int year = calendario.get(Calendar.YEAR);
                                    int month = calendario.get(Calendar.MONTH);
                                    int day = calendario.get(Calendar.DAY_OF_MONTH);
                                    DatePickerDialog fechaReserva = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            Calendar fechaSeleccionadaReserva = Calendar.getInstance();
                                            fechaSeleccionadaReserva.set(year, month, dayOfMonth);
                                            if (fechaSeleccionadaReserva.before(calendario)) {
                                                AlertDialog alertDialog = new AlertDialog.Builder(requireContext()).create();
                                                alertDialog.setTitle("Error");
                                                alertDialog.setMessage("La fecha seleccionada es anterior a la fecha actual." +
                                                        "\nPor favor, seleccione una fecha posterior a la fecha actual.\n" +
                                                        "Re-intenta");
                                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar", (dialog, which) -> {
                                                    dialog.dismiss();
                                                });
                                                alertDialog.show();
                                            } else {
                                                AlertDialog  alertDialog = new AlertDialog.Builder(requireContext()).create();
                                                alertDialog.setTitle("Seleccionar fecha de devolución");
                                                alertDialog.setMessage("Por favor, seleccione una fecha de devolución en la siguiente pantalla.");
                                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ir a seleccionar", (dialog1, which1) -> {
                                                    DatePickerDialog  fechaDevolucion = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                                                        @Override
                                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                                            // Comprobar que la fecha seleccionada es igual o superior a la de la reserva
                                                            Calendar fechaSeleccionadaDevolucion = Calendar.getInstance();
                                                            fechaSeleccionadaDevolucion.set(year, month, dayOfMonth);
                                                            if (fechaSeleccionadaDevolucion.before(fechaSeleccionadaReserva)) {
                                                                AlertDialog  alertDialog = new AlertDialog.Builder(requireContext()).create();
                                                                alertDialog.setTitle("Error");
                                                                alertDialog.setMessage("La fecha seleccionada es anterior a la fecha de reserva." +
                                                                        "\nPor favor, seleccione una fecha posterior a la fecha de reserva.\n" +
                                                                        "Re-intenta");
                                                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Re-intentar", (dialog, which) -> {
                                                                    dialog.dismiss();
                                                                });
                                                                alertDialog.show();
                                                            } else {
                                                                AlertDialog alertDialog2 = new AlertDialog.Builder(requireContext()).create();
                                                                alertDialog2.setTitle("Confirmar reserva de " + implementoActual.getNombreImplemento() + "?");
                                                                alertDialog2.setMessage("Al pulsar 'Confirmar', aceptas reservar el implemento seleccionado." +
                                                                        "\nEsta acción será revisada y aprobada manualmente por el personal correspondiente.");
                                                                alertDialog2.setButton(AlertDialog.BUTTON_NEUTRAL, "Confirmar", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        String implementoIdActual = implementoActual.getIdImplemento();
                                                                        // Obtener fecha de hoy en formato Date
                                                                        Date fechaHoy = new Date();
                                                                        // Obtener fecha de mañana
                                                                        Date fechaReserva = fechaSeleccionadaReserva.getTime();
                                                                        Date fechaDevolucion = fechaSeleccionadaDevolucion.getTime();

                                                                        ReservaImplemento nuevaReserva = new ReservaImplemento(fechaHoy, fechaReserva, fechaDevolucion, new Implementos(implementoIdActual), MainActivity.usuarioActual, false, false);
                                                                        nuevaReserva.setImplemento(implementoActual);
                                                                        dialog.dismiss();
                                                                        ProgressDialog  progressDialog = new ProgressDialog(requireContext());
                                                                        progressDialog.setTitle("Cargando...");
                                                                        progressDialog.setMessage("Espere unos segundos...");
                                                                        progressDialog.show();
                                                                        nuevaReserva.crearReserva(new CallbackReservaImplemento() {
                                                                            @Override
                                                                            public void onError(String mensaje) {
                                                                                AlertDialog alertDialog = new AlertDialog.Builder(requireContext()).create();
                                                                                alertDialog.setTitle("Error");
                                                                                alertDialog.setMessage(mensaje);
                                                                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar", (dialog, which) -> {
                                                                                    dialog.dismiss();
                                                                                });
                                                                                alertDialog.show();
                                                                            }

                                                                            @Override
                                                                            public void onInfoEncontrada(boolean estado) {
                                                                            }

                                                                            @Override
                                                                            public void onReservaRealizada(boolean estado) {
                                                                                if (estado) {
                                                                                    progressDialog.dismiss();
                                                                                    AlertDialog alertDialog = new AlertDialog.Builder(requireContext()).create();
                                                                                    alertDialog.setTitle("Reserva realizada");
                                                                                    alertDialog.setMessage("¡Tu reserva fue realizada con éxito!\n" +
                                                                                            "Se te informará a la brevedad si la reserva fue aprobada.");
                                                                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar", (dialog, which) -> {
                                                                                        dialog.dismiss();
                                                                                        getActivity().getSupportFragmentManager().popBackStack();
                                                                                    });
                                                                                    alertDialog.show();
                                                                                } else {
                                                                                    progressDialog.dismiss();
                                                                                }
                                                                            }
                                                                        });
                                                                    }
                                                                });
                                                                alertDialog2.show();
                                                            }

                                                        }
                                                    }, year, month, dayOfMonth);
                                                    fechaDevolucion.show();
                                                });
                                                alertDialog.show();
                                            }
                                        }
                                    }, year, month, day);
                                    fechaReserva.show();
                                });
                                alertDialog1.show();
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
