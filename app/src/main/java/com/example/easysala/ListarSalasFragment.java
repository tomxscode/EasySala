package com.example.easysala;

import static com.example.easysala.MainActivity.usuarioActual;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.easysala.models.Bloque;
import com.example.easysala.models.CallbackBloque;
import com.example.easysala.models.CallbackDia;
import com.example.easysala.models.CallbackHorario;
import com.example.easysala.models.CallbackImplemento;
import com.example.easysala.models.CallbackReservaSala;
import com.example.easysala.models.CallbackSala;
import com.example.easysala.models.Dia;
import com.example.easysala.models.Horario;
import com.example.easysala.models.Implementos;
import com.example.easysala.models.Reserva;
import com.example.easysala.models.ReservaImplemento;
import com.example.easysala.models.Salas;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListarSalasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarSalasFragment extends Fragment {

    private ListView listViewReservasSalas;
    private ArrayList<String> listaReservasSalas;
    private ArrayList<Horario> reservaSalas;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListarSalasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListarSalasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListarSalasFragment newInstance(String param1, String param2) {
        ListarSalasFragment fragment = new ListarSalasFragment();
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
        View root = inflater.inflate(R.layout.fragment_listar_salas, container, false);
        listViewReservasSalas = root.findViewById(R.id.lv_ListarSalas);
        listaReservasSalas = new ArrayList<>();
        reservaSalas = new ArrayList<>();
        retornarListaSalas();
        return root;
    }

    public void retornarListaSalas(){
        if(usuarioActual != null){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("horario").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documento : task.getResult()) {
                                Bloque bloque = new Bloque(documento.getString("bloque"));
                                Dia dia = new Dia(documento.getString("dia"));
                                Salas sala = new Salas(documento.getString("sala"));
                                Horario horario = new Horario(documento.getId(),false,bloque,dia,sala);
                                sala.obtenerInfo(new CallbackSala() {
                                    @Override
                                    public void onError(String error) {
                                    }
                                    @Override
                                    public void onObtenerInfo(boolean encontrado) {
                                        if(encontrado){
                                            horario.setSalaHorario(sala);
                                            bloque.obtenerInfo(new CallbackBloque() {
                                                @Override
                                                public void onError(String error) {

                                                }
                                                @Override
                                                public void onObtenerInfo(boolean encontrado) {
                                                    if(encontrado){
                                                        horario.setBloqueHorario(bloque);
                                                        dia.obtenerInfo(new CallbackDia() {
                                                            @Override
                                                            public void onError(String error) {

                                                            }
                                                            @Override
                                                            public void onObtenerInfo(boolean encontrado) {
                                                                if(encontrado){
                                                                    horario.setDiaHorario(dia);
                                                                    reservaSalas.add(horario);
                                                                    listaReservasSalas.add("Sala: "+ horario.getSalaHorario().getNombreSala()+
                                                                            "\nHora Inicio: " + horario.getBloqueHorario().getHoraInicio()+
                                                                            "\nHora Fin: " + horario.getBloqueHorario().getHoraFin()+
                                                                            "\nDía: "+ horario.getDiaHorario().getNombre());
                                                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, listaReservasSalas);
                                                                    listViewReservasSalas.setAdapter(adapter);
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            });

                                        }
                                    }
                                });
                                //insertar on click aqui
                            listViewReservasSalas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Horario reservaSeleccionada = reservaSalas.get(position);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                                    builder.setTitle("Confirmar Reserva?");
                                    builder.setMessage("¿Estás seguro de que deseas reservar esta sala?" +
                                            "\nSala:" + horario.getSalaHorario().getNombreSala()+
                                            "\nHora Inicio: "+ horario.getBloqueHorario().getHoraInicio()+
                                            "\nHora Fin: " + horario.getBloqueHorario().getHoraFin()+
                                            "\nDía: " + horario.getDiaHorario().getNombre());
                                    builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Reserva nuevaReserva = new Reserva(documento.getId(),false, horario, usuarioActual);
                                            nuevaReserva.crearReserva(new CallbackReservaSala() {
                                                @Override
                                                public void onError(String mensaje) {

                                                }

                                                @Override
                                                public void onInfoEncontrada(boolean estado) {

                                                }

                                                @Override
                                                public void onReservaRealizada(boolean estado) {
                                                    if (estado) {
                                                        AlertDialog alertDialog = new AlertDialog.Builder(requireContext()).create();
                                                        alertDialog.setTitle("Reserva realizada");
                                                        alertDialog.setMessage("¡Tu reserva fue realizada con éxito!\n" +
                                                                "Se te informará a la brevedad si la reserva fue aprobada.");
                                                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Aceptar", (dialog, which) -> {
                                                            dialog.dismiss();
                                                            getActivity().getSupportFragmentManager().popBackStack();
                                                        });
                                                        alertDialog.show();
                                                    }
                                                }
                                            });
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
}