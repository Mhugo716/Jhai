package com.example.jhaisales.ui.gallery;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;

import com.example.jhaisales.R;
import com.example.jhaisales.actualizar_productos;
import com.example.jhaisales.databinding.FragmentGalleryBinding;
import com.example.jhaisales.db.DB;
import com.example.jhaisales.home;
import com.example.jhaisales.ui.home.HomeFragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class GalleryFragment extends Fragment {
    ImageView imgProducto;
    int REQUEST_CODE_GALLERY =999;
    EditText nombreProducto,marca,precio,descripcion,categoria;
    Button btnproducto,btnimagen, btnFotoP;
    private FragmentGalleryBinding binding;
    DB db;

    Spinner spinnerCategoria;

    ArrayAdapter<CharSequence> spinnerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        GalleryViewModel galleryViewModel =
//                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Relacion con los componentes de la vista
        db = new DB(this.getContext());
        imgProducto = root.findViewById(R.id.imgP);
        nombreProducto = root.findViewById(R.id.nombreP);
        //marca = root.findViewById(R.id.marca);
        precio = root.findViewById(R.id.precio);
        descripcion = root.findViewById(R.id.descripcion);
        categoria = root.findViewById(R.id.categoria);
        btnproducto = root.findViewById(R.id.btnRegisProduc);
        btnimagen = root.findViewById(R.id.btnimagen);
        btnimagen.setOnClickListener(onimagen);
        btnproducto.setOnClickListener(onguardar);
        btnFotoP = root.findViewById(R.id.btnFoto);
        btnFotoP.setOnClickListener(camara);


        spinnerCategoria = root.findViewById(R.id.marca);
        spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opciones_menu, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(spinnerAdapter);




        return root;
    }


    View.OnClickListener onguardar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //Seleccion de las columnas de la Tabla Prodcustos
            String nombre = binding.nombreP.getText().toString().trim();
            String marca = binding.marca.getSelectedItem().toString();
            String precio = binding.precio.getText().toString();
            String descripcion = binding.descripcion.getText().toString();
            String categoria = binding.categoria.getText().toString();
            byte[] imagen = imageViewToByte(binding.imgP);

            //Verifica si los campos estan vacios
            if (nombre.isEmpty() || marca.isEmpty() || precio.isEmpty() ||  descripcion.isEmpty() || categoria.isEmpty() || imagen == null) {

                Toast.makeText(getActivity(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();

            }else{

                //Insersta los datos a la tabla de Productos
                boolean insert = db.insertarProducto(nombre, marca, precio,  descripcion, categoria, imagen);
                if (insert){

                    Toast.makeText(getActivity(), "Guardado", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getContext(),home.class);
                    startActivity(i);


                }else{

                    Toast.makeText(getActivity(), "El producto ya existe", Toast.LENGTH_SHORT).show();

                    db.close();

                }
            }
            //if(nombre.equals("") && marca.equals("") && precio.equals("") && descripcion.equals("") && categoria.equals("") && imagen.equals("")){
              //  Toast.makeText(getActivity(), "Campos vacios", Toast.LENGTH_SHORT).show();
           // }else{
               // boolean insert = db.insertarProducto(nombre,marca,precio,descripcion,categoria,imagen);
               // if(insert==true){
                   // Toast.makeText(getActivity(), "GUARDADO", Toast.LENGTH_SHORT).show();
                   // Intent intent = new Intent(getContext(),home.class);
                  //  startActivity(intent);

            //}
        }
    };

    //Verifica si la aplicación tiene el permiso(permiso para leer el almacenamiento externo, como archivos en la tarjeta SD) concedido o no.
    View.OnClickListener onimagen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {


            }requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_GALLERY);

          }
        };


    //Convierte una imagen representada por un objeto ImageView en un arreglo de bytes
    public static byte[] imageViewToByte(ImageView image) {

        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;

    }

    //Maneja la respuesta a la solicitud de permiso para acceder a la galería o selección de imágenes.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){

            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);

            }
            else {

                Toast.makeText(getActivity(), "No tienes permisos a la localizacion", Toast.LENGTH_SHORT).show();

            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //Maneja el resultado de la selección de una imagen de la galería.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {

                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgProducto.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {

                e.printStackTrace();

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private View.OnClickListener camara = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                abrirCamara.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    ActivityResultLauncher<Intent> abrirCamara = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Bundle extras = result.getData().getExtras();
                if (extras != null) {
                    Bitmap imagen = (Bitmap) extras.get("data");
                    if (imagen != null) {
                        imgProducto.setImageBitmap(imagen);
                    }
                }
            }
        }
    });


}