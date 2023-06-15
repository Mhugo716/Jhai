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
import com.example.jhaisales.db.Datos;

import org.json.JSONArray;

import java.util.List;

public class registro extends AppCompatActivity {
    Button btnRegister;
    EditText nombre, correo, password;
    DB db;
    private ActivityRegistroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Relacion con los objetos de la vista
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

            //Seleccion De columnas de la Tabla Usuarios
            String nombre = binding.etNombre.getText().toString();
            String correo = binding.etCorreo.getText().toString();
            String pass = binding.etPassword.getText().toString();

            if (nombre.equals("") && correo.equals("") && pass.equals("")) {//Verifica si los campos son vacios

                Toast.makeText(registro.this, "Campos vacios", Toast.LENGTH_SHORT).show();

            } else {

                    boolean usuarioExistente = db.usuarioExiste(nombre);
                    if (usuarioExistente) {
                        Toast.makeText(registro.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();

                    } else {

                        boolean correoExistente = db.correoExistente(correo);
                        if (correoExistente) {
                            Toast.makeText(registro.this, "El correo ya existe", Toast.LENGTH_SHORT).show();

                        } else {

                            boolean correcto = db.insertUsuario(nombre, correo, pass);//Inserta los datos a la Tabla Usuarios
                            if (correcto == true) {

                                Toast.makeText(registro.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                                login();

                            }

                        }
                    }
                }
            }
    };

        public void login() {//Manda a llamar la Pantalla de Inicio de Sesion

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);

        }

    }
