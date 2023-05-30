package com.example.jhaisales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jhaisales.databinding.ActivityMainBinding;
import com.example.jhaisales.db.DB;

import org.jetbrains.annotations.TestOnly;

public class MainActivity extends AppCompatActivity {
    EditText nombre,pass;
    Button btnlogin;
    DB db;
    TextView registro;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Relacion con los objetos de la vista

        db = new DB(this);

        nombre =  findViewById(R.id.etNombreL);
        pass = findViewById(R.id.etPasswordL);

        registro = findViewById(R.id.registro);
        btnlogin = findViewById(R.id.btnLogin);

        binding.btnLogin.setOnClickListener(logeo);
        registro.setOnClickListener(onregistro);
    }

    View.OnClickListener logeo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //Seleccion De columnas de la Tabla Usuarios
            String nombre = binding.etNombreL.getText().toString();
            String pass = binding.etPasswordL.getText().toString();

            if (nombre.equals("") && pass.equals("")){//Verifica si los campos son vacios

                Toast.makeText(MainActivity.this, "Campos vacios", Toast.LENGTH_SHORT).show();

            }else{

                boolean verifi = db.checkpass(pass,nombre);//Verifica si el Usuario existe en la Tabla Usuario
                if(verifi == true){

                    Toast.makeText(MainActivity.this, "Logeo Exitoso", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this,home.class);
                    startActivity(i);

                }
            }
        }
    };
    View.OnClickListener onregistro = new View.OnClickListener() {
        @Override
        public void onClick(View view) {//Manda a llamar a la pantalla Registro

            Intent i  = new Intent(MainActivity.this,registro.class);
            startActivity(i);

        }
    };

}