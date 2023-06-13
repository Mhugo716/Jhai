package com.example.jhaisales;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jhaisales.databinding.ActivityActualizarDatosClienteBinding;
import com.example.jhaisales.db.DB;
import com.example.jhaisales.db.Datos;

public class actualizar_datos_cliente extends AppCompatActivity {

    EditText nombreA, direccionA, telefonoA, referenciasA;
    Button btnActualizarCl, btnEliminarCL;

    ImageView atras;

    Datos datos;

    int id=0;

    DB db;

    private ActivityActualizarDatosClienteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActualizarDatosClienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nombreA = (EditText) findViewById(R.id.nombreCA);
        direccionA = (EditText) findViewById(R.id.direccionCA);
        telefonoA = (EditText) findViewById(R.id.telefonoCA);
        referenciasA = (EditText) findViewById(R.id.referenciasCA);
        btnActualizarCl = (Button) findViewById(R.id.btnDatosCA);
        atras = (ImageView) findViewById(R.id.Atras);
        btnEliminarCL = (Button) findViewById(R.id.btnEliminarCliente);

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
            nombreA.setText(datos.getColumna1());
            direccionA.setText(datos.getColumna2());
            telefonoA.setText(datos.getColumna3());
            referenciasA.setText(datos.getColumna4());
        }

        btnActualizarCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombreCliente = binding.nombreCA.getText().toString();
                String direccion = binding.direccionCA.getText().toString();
                String telefono = binding.telefonoCA.getText().toString();
                String referencias = binding.referenciasCA.getText().toString();

                if (nombreCliente.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || referencias.isEmpty()){

                    Toast.makeText(actualizar_datos_cliente.this, "Campos vacios", Toast.LENGTH_SHORT).show();

                }else{

                    boolean listo = db.modificarDatosCliente(id, nombreCliente, direccion, telefono, referencias);

                    if (listo == true){

                        Toast.makeText(actualizar_datos_cliente.this, "Datos Guardados Exitosamente", Toast.LENGTH_SHORT).show();

                        Intent intent =  new Intent(actualizar_datos_cliente.this, home.class);
                        startActivity(intent);

                    }
                }
            }
        });

        btnEliminarCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(actualizar_datos_cliente.this);
                builder.setTitle("Título del mensaje");
                builder.setMessage("¿Estás seguro de que deseas borrar?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Acciones a realizar al hacer clic en el botón "Aceptar" del diálogo
                        if(db.borrarCliente(id)) {

                            Toast.makeText(actualizar_datos_cliente.this, "CLIENTE BORRADO", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(actualizar_datos_cliente.this, home.class);
                            startActivity(i);
                        }
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Acciones a realizar al hacer clic en el botón "Cancelar" del diálogo

                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(actualizar_datos_cliente.this, home.class);
                startActivity(i);
            }
        });

    }
}