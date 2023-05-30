package com.example.jhaisales;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jhaisales.databinding.FragmentPedidosBinding;
import com.example.jhaisales.db.AdapterP;
import com.example.jhaisales.db.AdapterPD;
import com.example.jhaisales.db.DB;
import com.example.jhaisales.ui.home.HomeFragment;

import java.util.ArrayList;


public class pedidos extends Fragment {
    private FragmentPedidosBinding binding;
    private DB db;
    private ArrayList datos;
    private RecyclerView recyclerView;
    private Button btnNotificacion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPedidosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnNotificacion = root.findViewById(R.id.btnPedidos);

        db = new DB(this.getContext());

        datos = new ArrayList<>();

        recyclerView = root.findViewById(R.id.recyclerViewPedido);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        AdapterPD adapterPD = new AdapterPD(db.mostrarPedidos(), getContext());
        recyclerView.setAdapter(adapterPD);

        btnNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return root;
    }


}