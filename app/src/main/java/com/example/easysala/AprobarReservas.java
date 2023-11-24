package com.example.easysala;

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
import com.example.easysala.models.CallbackMarca;
import com.example.easysala.models.CallbackSala;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AprobarReservas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AprobarReservas extends Fragment {

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
            listaNomReservas = new ArrayList<>();

            retornarListaSalasFirebase();

            return view;
        }
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

        public void retornarListaSalasFirebase() {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("reserva_sala").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documento : task.getResult()) {
                            Boolean aprobada = documento.getBoolean("aprobada");
                            String idhorario = documento.getString("id_horario");
                            String idusuario = documento.getString("id_idusuario");
                            // Puedes agregar más campos según tus necesidades

                            // Agregar información al ArrayList
                            listaNomReservas.add(aprobada + " - " + idhorario + " - " + idusuario);
                        }

                        // Crear ArrayAdapter y asignarlo al ListView
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, listaNomReservas);
                        listReservas.setAdapter(adapter);
                    }
                }
            });
        }
    }