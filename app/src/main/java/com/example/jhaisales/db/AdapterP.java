package com.example.jhaisales.db;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jhaisales.R;
import com.example.jhaisales.actualizar_productos;
import com.example.jhaisales.carritoCompras;
import com.example.jhaisales.vistaProducto;

import java.util.ArrayList;

public class AdapterP extends RecyclerView.Adapter<AdapterP.ViewHolder> {
    static ArrayList<Datos>lista;

    public AdapterP(ArrayList<Datos> lista,Context context) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Visualizacion de los datos con la vista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productos,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        //Relacion de las columnas de las tablas con el AdapterP
        holder.id.setText(String.valueOf(lista.get(position).getId()));
        holder.columna1.setText(lista.get(position).getColumna1());
        holder.columna2.setText(lista.get(position).getColumna2());
        holder.columna3.setText(lista.get(position).getColumna3());

        Datos datos = lista.get(position);
        byte[] imagen = datos.getImagen();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagen, 0, imagen.length);
        holder.imagen.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder  extends RecyclerView.ViewHolder{
        private TextView id,columna1,columna2,columna3,columna4,columna5;
        private ImageView imagen;

        private ImageView btnVista, btncompra;

        public ViewHolder(@NonNull View View) {
            super(View);

            //Relacion de las columnas con los componentes de la vista
            id = View.findViewById(R.id.idproducto);
            columna1 = View.findViewById(R.id.product_name);
            columna2 = View.findViewById(R.id.product_description);
            columna3 = View.findViewById(R.id.product_price);
            imagen = View.findViewById(R.id.product_image);
            btnVista = View.findViewById(R.id.btnvistaProducto);
            btncompra = View.findViewById(R.id.btnCarrro);

            View.setOnClickListener(new View.OnClickListener() {//Boton para visualizar los datos del producto en la vista Actualizar
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent i = new Intent(context, actualizar_productos.class);
                    i.putExtra("idProducto",lista.get(getAdapterPosition()).getId());
                    context.startActivity(i);
                }
            });

            btnVista.setOnClickListener(new View.OnClickListener() {//Boton para visualizar el producto en la vistaProducto
                @Override
                public void onClick(android.view.View v) {
                    Context context = v.getContext();
                    Intent i = new Intent(context, vistaProducto.class);
                    i.putExtra("idProducto",lista.get(getAdapterPosition()).getId());
                    context.startActivity(i);
                }
            });

            btncompra.setOnClickListener(new View.OnClickListener() {//Manda a llamar a la vista Carrito visualizando todos los productos agregados
                @Override
                public void onClick(android.view.View v) {
                    Context context = v.getContext();
                    Intent i = new Intent(context, carritoCompras.class);
                    context.startActivity(i);
                }
            });

        }

    }
}
