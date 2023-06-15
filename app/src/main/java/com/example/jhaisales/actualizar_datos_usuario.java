package com.example.jhaisales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.jhaisales.databinding.ActivityActualizarDatosUsuarioBinding;
import com.example.jhaisales.databinding.FragmentSlideshowBinding;
import com.example.jhaisales.db.DB;
import com.example.jhaisales.db.Datos;

public class actualizar_datos_usuario extends AppCompatActivity {
    private DB db;
    private int id;
    private ActivityActualizarDatosUsuarioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActualizarDatosUsuarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        db = new DB(this);
        getPerfil();
        binding.btnActualizarPerfil.setOnClickListener(clickActualizar);
    }

    private void getPerfil(){

        id = getSharedPreferences("jhaisales", Context.MODE_PRIVATE).getInt("id_usuario", -1);
        if (id == -1){
            Toast.makeText(this, "Error al buscar el usuario", Toast.LENGTH_SHORT).show();
            finish();

        }

        Datos datos = db.mostrarDatosUsuario(id).get(0);
        binding.nombrePerfil.setText(datos.getColumna1());
        binding.correoPerfil.setText(datos.getColumna2());
        binding.passPerfil.setText(datos.getColumna3());
    }

    private View.OnClickListener clickActualizar = view ->{
        //String nombre = binding.nombrePerfil.getEditableText().toString();
        String correo = binding.correoPerfil.getEditableText().toString();
        String password = binding.passPerfil.getEditableText().toString();

        if(!correo.isEmpty() && !password.isEmpty()){

            if (db.modificarUsuario(id,correo,password)){
                finish();
            }

        }else{

            Toast.makeText(this, "Algun dato sin llenar", Toast.LENGTH_SHORT).show();

        }
    };
}