package com.example.jhaisales.db;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jhaisales.R;

import java.util.ArrayList;

public class AdapterUs extends RecyclerView.Adapter<AdapterUs.ViewHolder>{

    static ArrayList<Datos> lista;

    Context context;

    public AdapterUs(ArrayList<Datos> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterUs.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vistausuario,parent,false);
        return new AdapterUs.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUs.ViewHolder holder, int position) {

        holder.id.setText(String.valueOf(lista.get(position).getId()));
        holder.columna1.setText(lista.get(position).getColumna1());
        holder.columna2.setText(lista.get(position).getColumna2());
        holder.columna3.setText(lista.get(position).getColumna3());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id,columna1,columna2,columna3;
        public ViewHolder(@NonNull View View) {
            super(View);
            id = View.findViewById(R.id.idUsuario);
            columna1 = View.findViewById(R.id.nombreUs);
            columna2 = View.findViewById(R.id.correoUs);
            columna3 = View.findViewById(R.id.claveUs);
        }
    }
}
