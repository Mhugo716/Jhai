package com.example.jhaisales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.jhaisales.databinding.ActivityDatosClienteBinding;
import com.example.jhaisales.databinding.FragmentPedidosBinding;
import com.example.jhaisales.db.DB;

public class datos_cliente extends AppCompatActivity {

    Button btnDatosCliente, btnContinuar;
    DB db;
    ActivityDatosClienteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDatosClienteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new DB(this);

        btnDatosCliente = findViewById(R.id.btnCliente);
        btnDatosCliente.setOnClickListener(cliente);
    }


    View.OnClickListener cliente = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String nombreCliente = binding.etNombreC.getText().toString();
            String direccion = binding.etDireccion.getText().toString();
            String telefono = binding.etTelefono.getText().toString();
            String refencias = binding.etReferencias.getText().toString();

            if (nombreCliente.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || refencias.isEmpty()){
                Toast.makeText(datos_cliente.this, "Campos vacios", Toast.LENGTH_SHORT).show();
            }else{
                boolean correct = db.insertCliente(nombreCliente,direccion,telefono,refencias);
                if (correct == true){
                    Toast.makeText(datos_cliente.this, "Datos Guardados", Toast.LENGTH_SHORT).show();

                }
            }
        }
    };
}