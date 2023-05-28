package com.example.jhaisales.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jhaisales.R;
import com.example.jhaisales.databinding.FragmentHomeBinding;
import com.example.jhaisales.db.AdapterP;
import com.example.jhaisales.db.DB;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    DB db;
    ArrayList datos;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DB(this.getContext());

        datos = new ArrayList<>();

        recyclerView = root.findViewById(R.id.rcProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        AdapterP adapter = new AdapterP(db.mostrartodoProductos(),getContext());
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}