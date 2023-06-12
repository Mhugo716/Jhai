package com.example.jhaisales.ui.slideshow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jhaisales.R;
import com.example.jhaisales.actualizar_datos_usuario;
import com.example.jhaisales.databinding.FragmentSlideshowBinding;
import com.example.jhaisales.db.AdapterP;
import com.example.jhaisales.db.AdapterUs;
import com.example.jhaisales.db.DB;
import com.example.jhaisales.db.Datos;

import java.util.ArrayList;
import java.util.List;


public class SlideshowFragment extends Fragment {

//    TextView nombreUs, correoUs, clave;

    private FragmentSlideshowBinding binding;

//    int id = 0;
    private DB db;
//    private List<Datos> datos;
//
//    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        SlideshowViewModel slideshowViewModel =
//                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DB(this.getContext());

//        recyclerView = root.findViewById(R.id.usuario);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        AdapterUs adapterUS = new AdapterUs(db.mostrarDatosUsuario(), getContext());
//        recyclerView.setAdapter(adapterUS);
//        Datos datos = db.mostrarDatosUsuario().get(0);
//
//        binding.idPerfil.setText(""+datos.getId());
//        binding.nombrePerfil.setText(datos.getColumna1());
//        binding.correoPerfil.setText(datos.getColumna2());

        getPerfil();
        binding.btnActualizarPerfi.setOnClickListener(clickActualizarPerfil);
        return root;

    }

    private View.OnClickListener clickActualizarPerfil = view -> {
        Intent intent = new Intent(getContext(), actualizar_datos_usuario.class);
        startActivity(intent);
        onPause();
    };

    private void getPerfil(){
        int id = getActivity().getSharedPreferences("jhaisales", Context.MODE_PRIVATE).getInt("id_usuario", -1);
        if (id == -1){
            Toast.makeText(getContext(), "Error al buscar el usuario", Toast.LENGTH_SHORT).show();
        }else{
            Datos datos = db.mostrarDatosUsuario(id).get(0);
            binding.idPerfil.setText(""+datos.getId());
            binding.nombrePerfil.setText(datos.getColumna1());
            binding.correoPerfil.setText(datos.getColumna2());
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        if (db == null){
            db = new DB(this.getContext());
        }

        getPerfil();
        super.onResume();
    }
}