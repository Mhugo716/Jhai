package com.example.jhaisales;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jhaisales.databinding.FragmentDatosClienteBinding;
import com.example.jhaisales.db.DB;


public class datosCliente extends Fragment {

    EditText nombreCliente, direccion, telefono, referenciasCliente;

    Button btnDatosC;

    DB db;

    private FragmentDatosClienteBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDatosClienteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DB(this.getContext());
        nombreCliente = root.findViewById(R.id.nombreC);
        direccion = root.findViewById(R.id.direccionC);
        telefono = root.findViewById(R.id.telefonoC);
        referenciasCliente = root.findViewById(R.id.referenciasC);

        btnDatosC = root.findViewById(R.id.btnDatosC);
        btnDatosC.setOnClickListener(datosC);

        return root;
    }

    View.OnClickListener datosC = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String nombreCliente = binding.nombreC.getEditableText().toString();
            String direccion = binding.direccionC.getEditableText().toString();
            String telefono = binding.telefonoC.getEditableText().toString();
            String referencias = binding.referenciasC.getEditableText().toString();

            if(nombreCliente.equals("") && direccion.equals("") && telefono.equals("") && referencias.equals("")){//Verifica si los campos son vacios

                Toast.makeText(getActivity(), "Campos vacios", Toast.LENGTH_SHORT).show();

            }else{

                boolean datosC = db.insertCliente(nombreCliente, direccion, telefono, referencias);
                if (datosC){

                    Toast.makeText(getActivity(),"Datos Guardados Exitosamente", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getContext(), home.class);
                    startActivity(intent);
                }
            }
        }
    };

}
