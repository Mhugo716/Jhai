package com.example.jhaisales.db;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jhaisales.R;
import com.example.jhaisales.actualizar_productos;
import com.example.jhaisales.carritoCompras;
import com.example.jhaisales.home;
import com.example.jhaisales.pedidos;
import com.example.jhaisales.vistaProducto;

import java.util.ArrayList;

public class AdapterPV extends RecyclerView.Adapter<AdapterPV.ViewHolder> {

    static ArrayList<Datos>list;

    public AdapterPV(ArrayList<Datos> lista, Context context) {
        this.list = lista;
    }


    @NonNull
    @Override
    public AdapterPV.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vistacarrito,parent,false);
        return new AdapterPV.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPV.ViewHolder holder, int position) {

        holder.id.setText(String.valueOf(list.get(position).getId()));
        holder.columna1.setText(list.get(position).getColumna1());
        holder.columna2.setText(list.get(position).getColumna2());
        holder.columna3.setText(list.get(position).getColumna3());
        Log.e("cantidadRV", String.valueOf(list.get(position).getCantidad()));
        holder.cantidad.setText("" + list.get(position).getCantidad());

        Datos datos = list.get(position);
        byte[] imagen = datos.getImagen();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagen, 0, imagen.length);
        holder.imagen.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /*public void eliminar(){
        ArrayList<Datos> itemsremoved = new ArrayList<>();
        Datos  item;
        for (int i = 0; i< list.size(); i++){
            item = list.get(i);
            if(item.getId() == list.size()){
                itemsremoved.add(item);
                list.remove(i);
                break;
            }
        }
        this.notifyDataSetChanged();
    }*/

    /*public void borrar(){
        int count = list.size();
        list.clear();
        this.notifyItemRangeRemoved(0, count);

    }*/

        public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id,columna1,columna2,columna3,columna4,columna5, cantidad;
        private ImageView imagen;

        private Button btnDatosCliente;
        public ViewHolder(@NonNull View View) {
            super(View);
            id = View.findViewById(R.id.idproduct);
            columna1 = View.findViewById(R.id.productname);
            columna2 = View.findViewById(R.id.productdescription);
            columna3 = View.findViewById(R.id.price);
            imagen = View.findViewById(R.id.product_imag);
            cantidad = View.findViewById(R.id.cantidadproduct);
            btnDatosCliente = View.findViewById(R.id.btnCliente);



            View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    Context context = v.getContext();
                    Intent i = new Intent(context, vistaProducto.class);
                    i.putExtra("idProducto",list.get(getAdapterPosition()).getId());
                    context.startActivity(i);
                }
            });

            btnDatosCliente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {

                }
            });

        }
    }
}
