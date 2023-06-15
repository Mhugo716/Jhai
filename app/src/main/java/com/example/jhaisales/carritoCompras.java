package com.example.jhaisales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jhaisales.db.AdapterPV;
import com.example.jhaisales.db.DB;
import com.example.jhaisales.db.Datos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import kotlin.text.Regex;

public class carritoCompras extends AppCompatActivity {

    DB db;

    int id=0;

    Datos datos;

    RecyclerView recyclerView;

    private Button btnCompra, btnEliminar;

    private TextView eliminarCarrito;

    private ImageView atras;

    private ArrayList<Datos> productos;

    private Carrito carrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_compras);

        btnCompra = (Button) findViewById(R.id.btnComprarCarrito);
        btnEliminar = findViewById(R.id.btnEliminarCarrito);
        atras = (ImageView) findViewById(R.id.Atras);
        recyclerView = (RecyclerView) findViewById(R.id.carritoProductos);
        db = new DB(this);


        carrito = new Carrito(this);
        Set<String> carrito_productos = carrito.getProductos(); //Obtienes los productos

        productos = new ArrayList<>(); //Inicializas el arreglo que contendra los productos que tiene el carrito

        for(String id: carrito_productos){ //Recorres el arreglo que regreso la clase carrito
            String[] auxiliar = id.split(";");
            Log.e("Info", auxiliar[0] + " " + auxiliar[1]);

            Datos nomProducto = db.mostrarproducto((auxiliar[0]));//Extraemos el producto segun su id
            if (nomProducto != null){
                nomProducto.setCantidad(Integer.parseInt(auxiliar[1]));
                productos.add(nomProducto); //Arreglo con los productos
            }else{
                Log.e("Error", "El prodcuto con ID " + auxiliar[0] + "no existe");
            }

        }

        //Inicializar las opciones del recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AdapterPV adapter = new AdapterPV(productos, this);
        recyclerView.setAdapter(adapter);


        btnCompra.setOnClickListener(view ->{imprimirTicketDeCompra();});

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size =  productos.size();
                productos.clear();
                adapter.notifyItemRangeRemoved(0, size);
                carrito.vaciar();

               //Set<String> prodcutos = carrito.getProductos(); //¿?

               Log.e("Info","Cantidad de productos antes de limpiar: " + productos.size());
               //prodcutos.clear();

               //adapter.notifyDataSetChanged();
               Log.e("Info", "Cantidad de productos despues de limpiar: " + productos.size());

               Intent intent = new Intent(carritoCompras.this, home.class);
               startActivity(intent);
            }
        });

        atras.setOnClickListener(view -> {
            Intent i = new Intent(carritoCompras.this, home.class);
            startActivity(i);
        });
    }

    private void imprimirTicketDeCompra() {
        PrintHelper printHelper = new PrintHelper(carritoCompras.this);
        printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        printHelper.setColorMode(PrintHelper.COLOR_MODE_MONOCHROME);

        printHelper.printBitmap("Ticket de compra", generarBitmapDelTicket(), new PrintHelper.OnPrintFinishCallback() {
            @Override
            public void onFinish() {
                insertPedido();
                Toast.makeText(carritoCompras.this, "Ticket impreso", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

    }

    private Bitmap generarBitmapDelTicket() {

        Bitmap bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        canvas.drawText("Ticket", 200, 50, paint);
        canvas.drawText("JHAISALES", 200, 80, paint);
        canvas.drawText("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 50, 120, paint);
        int y = 100;
        int precio = 0;

        for(int i = 0; i < productos.size(); i++){
            y+=50;
            canvas.drawText( productos.get(i).getColumna1() + ":         " + productos.get(i).getColumna3(), 100, y, paint);

            precio += Integer.parseInt(productos.get(i).getColumna3());
        }

        canvas.drawText("Total De Compra     "+ precio, 100, y+50, paint);

        return bitmap;
    }

    private void insertPedido(){

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yy HH:mm", Locale.getDefault());
        String formattedDate = df.format(c);

        String nuevacadena = formattedDate.toUpperCase().replaceAll("[A-Z-:' '.]", "");

        for (Datos producto: productos) {

            int fechaPedido = nuevacadena.hashCode();

            db.insertarPedido(
                    producto.getColumna1(),
                    fechaPedido,
                    producto.getId(),
                    producto.getColumna5(),
                    producto.getColumna3(),
                    producto.getImagen()
            );
        }
            /*db.insertarPedido(producto.getColumna1(),
                    Integer.parseInt(nuevacadena),
                    producto.getId(),
                    producto.getColumna5(),
                    producto.getColumna3(),
                    producto.getImagen());*/



    }
}