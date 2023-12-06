package com.example.easysala;

import static com.example.easysala.MainActivity.usuarioActual;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.easysala.models.CallbackHorario;
import com.example.easysala.models.CallbackImplemento;
import com.example.easysala.models.Horario;
import com.example.easysala.models.Implementos;
import com.example.easysala.models.Reserva;
import com.example.easysala.models.ReservaImplemento;
import com.example.easysala.ui.implementos.listar_implementos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home_principal_fragmento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home_principal_fragmento extends Fragment {

    private ListView listViewReservasImplementos;
    private ArrayList<String> listaReservasImplementos;
    private ArrayList<ReservaImplemento> reservaImplementos;
    private ListView listViewReservasSalas;
    private ArrayList<String> listaReservasSalas;
    private ArrayList<Reserva> reservaSalas;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public home_principal_fragmento() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home_principal_fragmento.
     */
    // TODO: Rename and change types and number of parameters
    public static home_principal_fragmento newInstance(String param1, String param2) {
        home_principal_fragmento fragment = new home_principal_fragmento();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_principal_fragmento, container, false);
        listViewReservasImplementos = root.findViewById(R.id.lv_reservas);
        listViewReservasSalas = root.findViewById(R.id.lv_reservaSalas);
        listaReservasSalas = new ArrayList<>();
        reservaSalas = new ArrayList<>();
        listaReservasImplementos = new ArrayList<>();
        reservaImplementos = new ArrayList<>();
        Button btnListarImplemento = root.findViewById(R.id.btnReservarImplemento);
        Button btnReservarSalas = root.findViewById(R.id.btnReservarSalas);
        retornarReservasImplementos();
        retornarReservasSalas();

        btnListarImplemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarFragmento(new listar_implementos());
            }
        });
        btnReservarSalas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarFragmento(new ListarSalasFragment());
            }
        });

        return root;
    }

    private void cargarFragmento(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null); // Opcional, para agregar a la pila de retroceso
        fragmentTransaction.commit();
    }


    public void retornarReservasSalas(){
        if(usuarioActual != null){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("reserva_sala").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documento : task.getResult()) {
                            if(documento.getData().get("usuario").equals(usuarioActual.getUid_bd())){
                                boolean aprobado = documento.getBoolean("aprobada");
                                Horario horario = new Horario(documento.getString("horario"));
                                Reserva reserva = new Reserva(documento.getId(), aprobado, horario, usuarioActual);

                                horario.obtenerInfo(new CallbackHorario() {
                                    @Override
                                    public void onError(String mensaje) {

                                    }

                                    @Override
                                    public void onObtenerInfo(boolean encontrado) {
                                        if(encontrado){
                                            String ap = (reserva.isAprobada()) ? "Aprobada" : "No aprobada";
                                            reserva.setHorario(horario);
                                            reservaSalas.add(reserva);
                                            listaReservasSalas.add("Sala" + reserva.getHorario().getSalaHorario().getNombreSala()+ " | Día:" + reserva.getHorario().getDiaHorario().getNombre()+
                                                    " | Aprobada: " + ap);
                                            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, listaReservasSalas);
                                            listViewReservasSalas.setAdapter(adapter);
                                        }
                                    }
                                });

                            }
                        }

                    }
                }
            });
        }
    }
    public void retornarReservasImplementos(){
        if(usuarioActual != null){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("reserva_implemento").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documento : task.getResult()) {
                            if(documento.getData().get("usuario").equals(usuarioActual.getUid_bd())){
                                boolean aprobado = documento.getBoolean("aprobada");
                                boolean entregado = documento.getBoolean("entregado");
                                Date fecha_devolucion = documento.getDate("fecha_devolucion");
                                Date fecha_reserva = documento.getDate("fecha_reserva");
                                Date fecha_solicitud = documento.getDate("fecha_solicitud");
                                Implementos implemento = new Implementos(documento.getString("implemento"));
                                ReservaImplemento resImplemento = new ReservaImplemento(fecha_solicitud,fecha_reserva,fecha_devolucion, implemento,usuarioActual,aprobado,entregado);
                                implemento.obtenerInfo(new CallbackImplemento() {
                                    @Override
                                    public void onError(String mensaje) {
                                    }
                                    @Override
                                    public void onInfoCargada(boolean estado){
                                        if(estado){
                                            String ap = (aprobado) ? "Aprobada" : "No aprobada";
                                            resImplemento.setImplemento(implemento);
                                            reservaImplementos.add(resImplemento);
                                            listaReservasImplementos.add("Implemento : " + resImplemento.getImplemento().getNombreImplemento()+" | Aprobada: " + ap);
                                            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, listaReservasImplementos);
                                            listViewReservasImplementos.setAdapter(adapter);
                                        }
                                    }
                                });

                            }
                        }

                    }
                }
            });
        }
    }
}