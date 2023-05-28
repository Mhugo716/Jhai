package com.example.jhaisales;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jhaisales.databinding.FragmentNavAndreaBinding;
import com.example.jhaisales.databinding.FragmentNavVianneyBinding;
import com.example.jhaisales.db.AdapterP;
import com.example.jhaisales.db.DB;

import java.util.ArrayList;

public class nav_vianney extends Fragment {

    private FragmentNavVianneyBinding binding;
    DB db;
    ArrayList date;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNavVianneyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DB(this.getContext());

        date = new ArrayList<>();

        recyclerView = root.findViewById(R.id.productosVianney);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        AdapterP adapter = new AdapterP(db.mostrarVianney(),getContext());
        recyclerView.setAdapter(adapter);

        return root;
    }
}