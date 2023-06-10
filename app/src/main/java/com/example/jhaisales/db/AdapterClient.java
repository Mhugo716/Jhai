package com.example.jhaisales.db;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jhaisales.R;
import com.example.jhaisales.vistaProducto;
import com.example.jhaisales.vista_datos_cliente;

import java.util.ArrayList;

public class AdapterClient extends RecyclerView.Adapter<AdapterClient.ViewHolder>{

    static ArrayList<Datos>lista;

    public AdapterClient(ArrayList<Datos> lista, Context context) {
        this.lista = lista;
    }


    @NonNull
    @Override
    public AdapterClient.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vistacliente,parent,false);
        return new AdapterClient.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClient.ViewHolder holder, int position) {

        holder.id.setText(String.valueOf(lista.get(position).getId()));
        holder.columna1.setText(lista.get(position).getColumna1());
        holder.columna2.setText(lista.get(position).getColumna2());
        holder.columna3.setText(lista.get(position).getColumna3());
        holder.columna4.setText(lista.get(position).getColumna4());

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id,columna1,columna2,columna3,columna4;
        public ViewHolder(View view) {
            super(view);

            id = view.findViewById(R.id.idcliente);
            columna1 = view.findViewById(R.id.nombrecliente);
            columna2 = view.findViewById(R.id.direccioncliente);
            columna3 = view.findViewById(R.id.telefonocliente);
            columna4 = view.findViewById(R.id.referenciasCliente);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent i = new Intent(context, vista_datos_cliente.class);
                    i.putExtra("idCliente",lista.get(getAdapterPosition()).getId());
                    context.startActivity(i);
                }
            });
        }
    }
}
