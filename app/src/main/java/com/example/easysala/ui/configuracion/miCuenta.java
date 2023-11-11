package com.example.easysala.ui.configuracion;

import static androidx.fragment.app.FragmentManager.TAG;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easysala.MainActivity;
import com.example.easysala.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link miCuenta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class miCuenta extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public miCuenta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment miCuenta.
     */
    // TODO: Rename and change types and number of parameters
    public static miCuenta newInstance(String param1, String param2) {
        miCuenta fragment = new miCuenta();
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
        View vista = inflater.inflate(R.layout.fragment_mi_cuenta, container, false);
        // Llenar información
        EditText etNombre, etApellido, etCorreo, etRol;
        etNombre = vista.findViewById(R.id.nombreTxt);
        etApellido = vista.findViewById(R.id.apellidoTxt);
        etCorreo = vista.findViewById(R.id.correoTxt);
        etRol = vista.findViewById(R.id.rolTxt);

        etNombre.setText(MainActivity.usuarioActual.getNombre());
        etApellido.setText(MainActivity.usuarioActual.getApellido());
        etCorreo.setText(MainActivity.usuarioActual.getCorreo());
        switch (MainActivity.usuarioActual.getRol()) {
            case 1:
                etRol.setText(getString(R.string.txt_rol_usuario));
                break;
            case 2:
                etRol.setText(getString(R.string.txt_rol_alumno));
                break;
            case 3:
                etRol.setText(getString(R.string.txt_rol_profesor));
                break;
            case 4:
                etRol.setText(getString(R.string.txt_rol_reg_academico));
                break;
            case 5:
                etRol.setText(getString(R.string.txt_rol_administrador));
                break;
            default:
                etRol.setText(getString(R.string.txt_rol_invalido));
                break;
        }
//        etRol.setText(MainActivity.usuarioActual.getRol());

        return vista;
    }
}