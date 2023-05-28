package com.example.jhaisales;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jhaisales.databinding.FragmentPedidosBinding;
import com.example.jhaisales.db.AdapterP;
import com.example.jhaisales.db.AdapterPD;
import com.example.jhaisales.db.DB;

import java.util.ArrayList;


public class pedidos extends Fragment {

    private FragmentPedidosBinding binding;
    DB db;
    ArrayList datos;
    RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPedidosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DB(this.getContext());

        datos = new ArrayList<>();

        recyclerView = root.findViewById(R.id.recyclerViewPedido);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));



        return root;
    }
}