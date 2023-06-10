package com.example.jhaisales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jhaisales.databinding.ActivityVistaDatosClienteBinding;
import com.example.jhaisales.db.DB;
import com.example.jhaisales.db.Datos;

public class vista_datos_cliente extends AppCompatActivity {

    TextView nombreCl, direccionCl, telefonoCl,referenciasCl;

    ImageView atrasCL;

    private ActivityVistaDatosClienteBinding binding;
    DB db;
    Datos datos;
    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVistaDatosClienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nombreCl = (TextView) findViewById(R.id.nombreCV);
        direccionCl = (TextView) findViewById(R.id.direccionCV);
        telefonoCl = (TextView) findViewById(R.id.telefonoCV);
        referenciasCl = (TextView) findViewById(R.id.referenciasCV);
        atrasCL = (ImageView) findViewById(R.id.Atras);


        if (savedInstanceState != null) {
            id = savedInstanceState.getInt("idCliente", 0);
        } else {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                id = extras.getInt("idCliente", 0);
            }
        }

        db = new DB(this);
        datos = db.mostrarCliente(String.valueOf(id));

        if (datos != null) {
            nombreCl.setText(datos.getColumna1());
            direccionCl.setText(datos.getColumna2());
            telefonoCl.setText(datos.getColumna3());
            referenciasCl.setText(datos.getColumna4());
        }

        atrasCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(vista_datos_cliente.this, home.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("idCliente", id);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        id = savedInstanceState.getInt("idCliente", 0);
    }



}