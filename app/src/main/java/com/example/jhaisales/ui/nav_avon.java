package com.example.jhaisales.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jhaisales.R;
import com.example.jhaisales.databinding.FragmentNavAvonBinding;
import com.example.jhaisales.db.AdapterP;
import com.example.jhaisales.db.DB;

import java.util.ArrayList;

public class nav_avon extends Fragment {

    Button btnVista;

    private FragmentNavAvonBinding binding;
    DB db;
    ArrayList date;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNavAvonBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DB(this.getContext());

        date = new ArrayList<>();

        btnVista =  root.findViewById(R.id.btnvistaProducto);


        recyclerView = root.findViewById(R.id.productosAvon);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        AdapterP adapter = new AdapterP(db.mostrarAvon(),getContext());
        recyclerView.setAdapter(adapter);

        return root;
    }
}