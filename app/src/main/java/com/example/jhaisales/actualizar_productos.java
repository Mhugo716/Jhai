package com.example.jhaisales;

import static com.example.jhaisales.ui.gallery.GalleryFragment.imageViewToByte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jhaisales.databinding.ActivityActualizarProductosBinding;
import com.example.jhaisales.databinding.ActivityMainBinding;
import com.example.jhaisales.databinding.FragmentGalleryBinding;
import com.example.jhaisales.db.DB;
import com.example.jhaisales.db.Datos;

public class actualizar_productos extends AppCompatActivity {
    ImageView imgProducto;
    EditText nombreProducto,marca,precio,descripcion,categoria;
    Button btnproducto,btnimagen,btnborrar;
    private ActivityActualizarProductosBinding binding;
    DB db;

    Datos datos;
    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActualizarProductosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Relacion con los objetos de la vista
        imgProducto = findViewById(R.id.imgPA);
        nombreProducto = findViewById(R.id.nombrePA);
        marca = findViewById(R.id.marcaA);
        precio = findViewById(R.id.precioA);
        descripcion = findViewById(R.id.descripcionA);
        categoria = findViewById(R.id.categoriaA);

        btnproducto = findViewById(R.id.btnRegisProducA);
        btnproducto.setOnClickListener(actualizar);
        btnimagen = findViewById(R.id.btnimagenA);
        btnborrar = findViewById(R.id.btnEliminar);
        btnborrar.setOnClickListener(borrar);

        //Determina si la actividad se está creando por primera vez o si se está restaurando después de un cambio de configuración.
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

        //Utiliza una instancia de la clase DB para obtener los datos de un producto específico de la base de datos.
        db = new DB(this);
        datos = db.mostrarproducto(""+ id);

        if (datos != null){

            //muestra la imagen del producto y los valores de varias columnas en la interfaz de usuario.
            byte[] img  = datos.getImagen();
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            imgProducto.setImageBitmap(bitmap);
            nombreProducto.setText(datos.getColumna1());
            marca.setText(datos.getColumna2());
            precio.setText(datos.getColumna3());
            descripcion.setText(datos.getColumna4());
            categoria.setText(datos.getColumna5());

        }


    }

    View.OnClickListener actualizar = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            //Seleccion de las columnas de la Tabla Producto
            String nombreProducto = binding.nombrePA.getText().toString();
            String marca = binding.marcaA.getText().toString();
            Double precio = Double.valueOf(binding.precioA.getText().toString());
            String descripcion = binding.descripcionA.getText().toString();
            String categoria = binding.categoriaA.getText().toString();
            byte[] imagen = imageViewToByte(binding.imgPA);

            //Verifica si los campos se encuentran
            if (nombreProducto.equals("")&&  descripcion.equals("")&& precio.equals("")&& marca.equals("")&& categoria.equals("")&& imagen.equals("")){

                Toast.makeText(actualizar_productos.this, "Campos vacios", Toast.LENGTH_SHORT).show();

          }else{
                //Inserta los nuevos datos del producto a la Tabla Productos
                boolean correcto = db.modificarproducto(id, nombreProducto,imagen,marca,precio,descripcion,categoria);

                if (correcto==true){
                    Toast.makeText(actualizar_productos.this, "Guardado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(actualizar_productos.this,home.class);
                    startActivity(intent);

                }else{

                    Toast.makeText(actualizar_productos.this, "PASO ALGO MAL", Toast.LENGTH_SHORT).show();

                }
            }
        }
    };

    //Borra el producto de la Tabla Productos
    View.OnClickListener borrar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(db.borrarProducto(id)){
                Toast.makeText(actualizar_productos.this, "PRODUCTO BORRADO", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(actualizar_productos.this,home.class);
                startActivity(i);

            }
        }
    };
}