package com.example.jhaisales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jhaisales.databinding.ActivityActualizarProductosBinding;
import com.example.jhaisales.databinding.ActivityRegistroBinding;
import com.example.jhaisales.databinding.ActivityVistaProductoBinding;
import com.example.jhaisales.db.DB;
import com.example.jhaisales.db.Datos;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class vistaProducto extends AppCompatActivity {

    ImageView imgProducto, atras;

    TextView nombreProducto,marca,precioV,descripcionV, cantidadProducto;

    Button btnCarrito, btnMasProduct, btnMenosProduct, btnCompra;

    private ActivityVistaProductoBinding binding;

    DB db;

    Datos datos;
    int id=0;

    int cantidad = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVistaProductoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imgProducto = (ImageView) findViewById(R.id.imgPV);
        nombreProducto = (TextView) findViewById(R.id.nombrePV);
        precioV = (TextView) findViewById(R.id.precioPV);
        descripcionV = (TextView) findViewById(R.id.descripcionPV);
        btnCarrito = (Button) findViewById(R.id.btnAgregarCarrito);
        btnMasProduct = (Button) findViewById(R.id.btnMasProducto);
        btnMenosProduct = (Button) findViewById(R.id.btnMenosProducto);
        cantidadProducto = (TextView) findViewById(R.id.txtCantidad);
        btnCompra = (Button) findViewById(R.id.btnComprar);
        atras = (ImageView) findViewById(R.id.Atras);

        if(savedInstanceState ==null){

            Bundle extra = getIntent().getExtras();

            if (extra==null){

                id = Integer.parseInt(null);

            }else{

                id = extra.getInt("idProducto");

            }
        }else{

            id =(int) savedInstanceState.getSerializable("idProducto");

        }

        db = new DB(this);
        datos = db.mostrarproducto("" + id);

        if (datos != null){

            byte[] img  = datos.getImagen();
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            imgProducto.setImageBitmap(bitmap);
            nombreProducto.setText(datos.getColumna1());
            descripcionV.setText(datos.getColumna4());
            precioV.setText(datos.getColumna3());

        }

        btnCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Carrito carrito = new Carrito(getApplicationContext());
                carrito.agregar(id + ";"+ cantidadProducto.getText().toString());
                Intent i = new Intent(getApplication(), carritoCompras.class);
                startActivity(i);
                Toast.makeText(vistaProducto.this, "Se agrego al carrito", Toast.LENGTH_SHORT).show();

            }
        });

        btnMasProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cantidad ++;
                cantidadProducto.setText("" + cantidad);

            }
        });

        btnMenosProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cantidad>1){

                    cantidad --;

                }

                cantidadProducto.setText("" + cantidad);

            }
        });

        btnCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imprimirTicketDeCompra();




            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(vistaProducto.this, home.class);
                startActivity(i);

            }
        });

    }

        private void imprimirTicketDeCompra() {

            PrintHelper printHelper = new PrintHelper(vistaProducto.this);

            printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
            printHelper.setColorMode(PrintHelper.COLOR_MODE_MONOCHROME);

            printHelper.printBitmap("Ticket de compra", generarBitmapDelTicket(nombreProducto.getText().toString(),precioV.getText().toString()), new PrintHelper.OnPrintFinishCallback() {
                @Override
                public void onFinish() {
                    insertPedido();

                    Toast.makeText(vistaProducto.this, "Ticket impreso", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

        }

    private Bitmap generarBitmapDelTicket(String nombre, String precio) {

        Bitmap bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        canvas.drawText("Ticket", 200, 60, paint);
        canvas.drawText("JHAISALES", 200, 90, paint);
        canvas.drawText("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 5, 120, paint);
        canvas.drawText(nombre + " ;         $" + precio, 100, 150, paint);
        //canvas.drawText("Producto 2: $20", 100, 200, paint);
        canvas.drawText("Total De Compra     "+ precio, 100, 250, paint);
        return bitmap;

    }

    private void insertPedido(){

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yy HH:mm", Locale.getDefault());
        String formattedDate = df.format(c);

        String nuevacadena = formattedDate.toUpperCase().replaceAll("[A-Z-:' '.]", "");
        Log.e("numeropedido", nuevacadena);

        db.insertarPedido(
                datos.getColumna1(),
                Integer.parseInt(nuevacadena),
                datos.getId(),datos.getColumna3(),
                datos.getColumna4(),
                datos.getImagen());

    }

}