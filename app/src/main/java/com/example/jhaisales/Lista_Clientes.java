package com.example.jhaisales;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jhaisales.databinding.FragmentListaClientesBinding;
import com.example.jhaisales.db.AdapterClient;
import com.example.jhaisales.db.AdapterP;
import com.example.jhaisales.db.DB;

import java.util.ArrayList;


public class Lista_Clientes extends Fragment {

    ArrayList data;
    DB db;
    RecyclerView recyclerView;
    FragmentListaClientesBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentListaClientesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DB(this.getContext());

        data = new ArrayList<>();
        recyclerView = root.findViewById(R.id.listaClientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        AdapterClient adapter = new AdapterClient(db.mostrarDatosCliente(),getContext());
        recyclerView.setAdapter(adapter);

        return root;
    }
}