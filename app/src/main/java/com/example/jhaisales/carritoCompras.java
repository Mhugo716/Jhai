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

import com.example.jhaisales.db.AdapterPV;
import com.example.jhaisales.db.DB;
import com.example.jhaisales.db.Datos;

import java.util.ArrayList;
import java.util.Set;

public class carritoCompras extends AppCompatActivity {

    DB db;

    int id=0;

    Datos datos;

    RecyclerView recyclerView;

    Button btnCompra, btnEliminar;

    TextView eliminarCarrito;

    ImageView atras;

    private ArrayList<Datos> productos, carrito;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_compras);

        btnCompra = (Button) findViewById(R.id.btnComprarCarrito);
        btnEliminar = findViewById(R.id.btnEliminarCarrito);
        atras = (ImageView) findViewById(R.id.Atras);
        recyclerView = (RecyclerView) findViewById(R.id.carritoProductos);
        db = new DB(this);


        Carrito carrito = new Carrito(this);
        Set<String> carrito_productos = carrito.getProductos();

            productos = new ArrayList<>();
        for(String id: carrito_productos){
            String[] auxiliar = id.split(";");

            Log.e("Info", auxiliar[0] + " " + auxiliar[1]);

            Datos nomProducto = db.mostrarproducto((auxiliar[0]));

            if (nomProducto != null){
                nomProducto.setCantidad(Integer.parseInt(auxiliar[1]));
                productos.add(nomProducto);
            }else{
                Log.e("Error", "El prodcuto con ID " + auxiliar[0] + "no existe");
            }

            //nomProducto.setCantidad(Integer.parseInt(auxiliar[1]));

            //productos.add(nomProducto);

        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AdapterPV adapter = new AdapterPV(productos, this);
        recyclerView.setAdapter(adapter);

        btnCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              imprimirTicketDeCompra();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Set<String> prodcutos = carrito.getProductos();
               Log.e("Info","Cantidad de productos antes de limpiar: " + prodcutos.size());
               prodcutos.clear();
               adapter.notifyDataSetChanged();
               Log.e("Info", "Cantidad de productos despues de limpiar: " + prodcutos.size());

               Intent intent = new Intent(carritoCompras.this, home.class);
               startActivity(intent);
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(carritoCompras.this, home.class);
                startActivity(i);
            }
        });
    }

    private void imprimirTicketDeCompra() {
        PrintHelper printHelper = new PrintHelper(carritoCompras.this);

        printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        printHelper.setColorMode(PrintHelper.COLOR_MODE_MONOCHROME);

        printHelper.printBitmap("Ticket de compra", generarBitmapDelTicket(), new PrintHelper.OnPrintFinishCallback() {
            @Override
            public void onFinish() {
                Toast.makeText(carritoCompras.this, "Ticket impreso", Toast.LENGTH_SHORT).show();
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
}