package com.example.jhaisales;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jhaisales.databinding.FragmentNavAvonBinding;
import com.example.jhaisales.databinding.FragmentNavBettewareBinding;
import com.example.jhaisales.db.AdapterP;
import com.example.jhaisales.db.DB;

import java.util.ArrayList;

public class nav_betteware extends Fragment {

    private FragmentNavBettewareBinding binding;
    DB db;
    ArrayList date;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNavBettewareBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DB(this.getContext());

        date = new ArrayList<>();

        //Relacion con el recyclerView de la vista
        recyclerView = root.findViewById(R.id.productosBetteware);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //Manda a llamar al Adapter para visualizar los prodcutos en la pantalla con la funcion mostrarProductos
        AdapterP adapter = new AdapterP(db.mostrarBetteware(),getContext());
        recyclerView.setAdapter(adapter);

        return root;
    }
}