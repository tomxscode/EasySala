package com.example.easysala;

import static com.example.easysala.MainActivity.usuarioActual;

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
import com.example.easysala.databinding.FragmentHomeBinding;
import com.example.easysala.models.CallbackHorario;
import com.example.easysala.models.CallbackImplemento;
import com.example.easysala.models.CallbackMarca;
import com.example.easysala.models.CallbackSala;
import com.example.easysala.models.CallbackTipoImplemento;
import com.example.easysala.models.Horario;
import com.example.easysala.models.Implementos;
import com.example.easysala.models.Marca;
import com.example.easysala.models.Modelo;
import com.example.easysala.models.Reserva;
import com.example.easysala.models.ReservaImplemento;
import com.example.easysala.models.TipoImplemento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AprobarReservas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AprobarReservas extends Fragment {

    private ListView listViewReservaSalas;
    private ArrayList<String> listaReservaSalas;
    private ArrayList<Reserva> reservaSalas;
    private ListView listReservas;
    private FragmentHomeBinding binding;
    private ArrayList<String> listaNomReservas;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AprobarReservas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AprobarReservas.
     */
    // TODO: Rename and change types and number of parameters
    public static AprobarReservas newInstance(String param1, String param2) {
        AprobarReservas fragment = new AprobarReservas();
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
            View view = inflater.inflate(R.layout.fragment_aprobar_reservas, container, false);

            listReservas = view.findViewById(R.id.list_reservas_aprobar);
            listViewReservaSalas = view.findViewById(R.id.list_reservas_aprobar);

            listaNomReservas = new ArrayList<>();
            listaReservaSalas = new ArrayList<>();
        reservaSalas = new ArrayList<>();



        retornarReservasSalas();

            return view;
        }
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    List<Reserva> ListaReservasSala = new ArrayList<>();
    private String formatearFechaResumida(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        return formato.format(fecha);
    }
    public void retornarReservasSalas(){
        if(usuarioActual != null){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("reserva_sala").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documento : task.getResult()) {
                                boolean aprobado = documento.getBoolean("aprobada");
                                Horario horario = new Horario(documento.getString("horario"));
                                Reserva revSala = new Reserva(documento.getId(), aprobado, horario);
                                horario.obtenerInfo(new CallbackHorario() {
                                    @Override
                                    public void onError(String mensaje) {

                                    }
                                    @Override
                                    public void onObtenerInfo(boolean encontrado) {
                                        if(encontrado){
                                            revSala.setHorario(horario);
                                            reservaSalas.add(revSala);
                                            listaReservaSalas.add("Aprobada: " + aprobado + " \nHora inicio: " + revSala.getHorario().getBloqueHorario().getHoraInicio()+
                                                    " \nHora fin: "+ revSala.getHorario().getBloqueHorario().getHoraFin() + " \nDía: " + revSala.getHorario().getDiaHorario().getNombre()+
                                                    " \nSala: " + revSala.getHorario().getSalaHorario().getNombreSala());
                                            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, listaReservaSalas);
                                            listViewReservaSalas.setAdapter(adapter);
                                        }
                                    }
                                });
                            listReservas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Reserva reservaSeleccionada = reservaSalas.get(position);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                                    builder.setTitle("Confirmar Aprobación");
                                    builder.setMessage("¿Estás seguro de aprobar esta reserva?");
                                    builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            actualizarEstadoReserva(reservaSeleccionada);
                                        }
                                    });
                                    builder.setNegativeButton("No", null); // Si se selecciona "No", no se realiza ninguna acción
                                    builder.show();
                                }
                            });
                        }
                    }
                }
            });
        }

    }

    public void limpiarListaSalas() {
        listaNomReservas.clear();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, listaNomReservas);
        listReservas.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //listaNomReservas.add("Estado: " + aprobada + " " + nombreImplementoReserva + " " + formatearFechaResumida(fecha_reserva));

   // ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, listaNomReservas);
               //                     listReservas.setAdapter(adapter);
              //                      progressDialog.dismiss();
            //                        listReservas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    //    @Override
     //   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       //     Reserva reservaSeleccionada = ListaReservasSala.get(position);
       //     Log.d("Listas", String.valueOf(position));
      //      Log.d("Listas", reservaSeleccionada.getDocumentId());
      //      AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        //    builder.setTitle("Confirmar Aprobación");
        //    builder.setMessage("¿Estás seguro de aprobar esta reserva?");
        //    builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
        //        @Override
        //        public void onClick(DialogInterface dialog, int which) {
        //            actualizarEstadoReserva(reservaSeleccionada);
       //         }
      //      });
      //      builder.setNegativeButton("No", null); // Si se selecciona "No", no se realiza ninguna acción

     //       builder.show();
    //    }

    private void actualizarEstadoReserva(Reserva reserva) {
        String documentId = reserva.getDocumentId();
        if (documentId != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("reserva_sala").document(documentId).update("aprobada", true)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            listaReservaSalas.remove("Estado: " + reserva);
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, listaReservaSalas);
                            listViewReservaSalas.setAdapter(adapter);
                            Toast.makeText(requireContext(), "Reserva aprobada correctamente", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Maneja el caso de error
                            Toast.makeText(requireContext(), "Error al aprobar la reserva", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(requireContext(), "Error: Identificador del documento nulo", Toast.LENGTH_SHORT).show();
        }
    }
    }