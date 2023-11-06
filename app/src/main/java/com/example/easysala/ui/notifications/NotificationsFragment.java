package com.example.easysala.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.easysala.R;
import com.example.easysala.databinding.FragmentNotificationsBinding;
import com.example.easysala.ui.login.LoginFragment;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button btn2 = (Button) root.findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarFragmento(new LoginFragment());
            }
        });

        return root;
    }


    public void cargarFragmento(Fragment fragmento) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.remove(new NotificationsFragment());
        fragmentTransaction.replace(R.id.frameLayout, fragmento);
        fragmentTransaction.commit();
    }

    public void botonAccion(View v) {
        cargarFragmento(new LoginFragment());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}