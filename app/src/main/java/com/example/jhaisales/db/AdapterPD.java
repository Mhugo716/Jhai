package com.example.jhaisales.db;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jhaisales.R;
import com.example.jhaisales.pedidos;
import com.example.jhaisales.vistaProducto;

import java.util.ArrayList;

public class AdapterPD extends RecyclerView.Adapter<AdapterPD.ViewHolder> {

    static ArrayList<Datos> lista;

    public AdapterPD(ArrayList<Datos> lista, Context context) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public AdapterPD.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vistapedido,parent,false);
        return new AdapterPD.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPD.ViewHolder holder, int position) {

        holder.id.setText(String.valueOf(lista.get(position).getId()));
        holder.columna1.setText(lista.get(position).getColumna1());
        holder.columna2.setText(lista.get(position).getColumna2());
        holder.columna3.setText(lista.get(position).getColumna3());
        Log.e("cantidadRV", String.valueOf(lista.get(position).getCantidad()));
        holder.cantidad.setText("" + lista.get(position).getCantidad());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id,columna1,columna2,columna3,columna4,columna5, cantidad;

        private ImageView imagen;
        private Button btnComprarP;
        public ViewHolder(@NonNull View View) {
            super(View);
            id = View.findViewById(R.id.idproduct);
            columna1 = View.findViewById(R.id.productname);
            columna2 = View.findViewById(R.id.productdescription);
            columna3 = View.findViewById(R.id.price);
            imagen = View.findViewById(R.id.product_imag);
            cantidad = View.findViewById(R.id.cantidadproduct);
            btnComprarP = View.findViewById(R.id.btnComprar);

            btnComprarP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    Context context = v.getContext();
                    Intent i = new Intent(context, pedidos.class);
                    i.putExtra("idProducto", lista.get(getAdapterPosition()).getId());
                }
            });
        }
    }
}
