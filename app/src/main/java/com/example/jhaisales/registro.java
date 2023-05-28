package com.example.jhaisales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jhaisales.databinding.ActivityRegistroBinding;
import com.example.jhaisales.db.DB;

public class registro extends AppCompatActivity {
    Button btnRegister;
    EditText nombre, correo, password;
    DB db;
    ActivityRegistroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new DB(this);
        nombre = findViewById(R.id.etNombre);
        correo = findViewById(R.id.etCorreo);
        password = findViewById(R.id.etPassword);

        btnRegister = findViewById(R.id.btnRegistro);
        btnRegister.setOnClickListener(registro);

    }

    View.OnClickListener registro = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String nombre = binding.etNombre.getText().toString();
            String correo = binding.etCorreo.getText().toString();
            String pass = binding.etPassword.getText().toString();

            if(nombre.equals("") && correo.equals("") && pass.equals("")){

                Toast.makeText(registro.this, "Campos vacios", Toast.LENGTH_SHORT).show();

            }else {

                boolean correcto = db.insertUsuario(nombre,correo,pass);
                if(correcto == true){

                    Toast.makeText(registro.this, "Registro completo", Toast.LENGTH_SHORT).show();
                    login();

                }
            }
        }
    };

    public void login(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}