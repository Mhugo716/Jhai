package com.example.jhaisales.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jhaisales.R;
import com.example.jhaisales.databinding.FragmentSlideshowBinding;
import com.example.jhaisales.db.AdapterP;
import com.example.jhaisales.db.AdapterUs;
import com.example.jhaisales.db.DB;
import com.example.jhaisales.db.Datos;

import java.util.ArrayList;
import java.util.List;


public class SlideshowFragment extends Fragment {

    TextView nombreUs, correoUs, clave;

    private FragmentSlideshowBinding binding;

    int id = 0;
    DB db;
    private List<Datos> datos;

    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DB(this.getContext());

        recyclerView = root.findViewById(R.id.usuario);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AdapterUs adapterUS = new AdapterUs(db.mostrarDatosUsuario(), getContext());
        recyclerView.setAdapter(adapterUS);


        return root;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }
}