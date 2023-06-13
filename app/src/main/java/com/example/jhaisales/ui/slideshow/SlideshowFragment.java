package com.example.jhaisales.ui.slideshow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jhaisales.R;
import com.example.jhaisales.actualizar_datos_usuario;
import com.example.jhaisales.databinding.FragmentSlideshowBinding;
import com.example.jhaisales.db.AdapterP;
import com.example.jhaisales.db.AdapterUs;
import com.example.jhaisales.db.DB;
import com.example.jhaisales.db.Datos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SlideshowFragment extends Fragment {

//    TextView nombreUs, correoUs, clave;
private Datos dato;
    private FragmentSlideshowBinding binding;

//    int id = 0;
    private DB db;
//    private List<Datos> datos;
//
//    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        SlideshowViewModel slideshowViewModel =
//                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DB(this.getContext());

//        recyclerView = root.findViewById(R.id.usuario);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        AdapterUs adapterUS = new AdapterUs(db.mostrarDatosUsuario(), getContext());
//        recyclerView.setAdapter(adapterUS);
//        Datos datos = db.mostrarDatosUsuario().get(0);
//
//        binding.idPerfil.setText(""+datos.getId());
//        binding.nombrePerfil.setText(datos.getColumna1());
//        binding.correoPerfil.setText(datos.getColumna2());

        getPerfil();
        binding.btnActualizarPerfi.setOnClickListener(clickActualizarPerfil);

        binding.btnSincron.setOnClickListener(sincro);

        return root;

    }

    private View.OnClickListener clickActualizarPerfil = view -> {
        Intent intent = new Intent(getContext(), actualizar_datos_usuario.class);
        startActivity(intent);
        onPause();
    };

    private void getPerfil(){
        int id = getActivity().getSharedPreferences("jhaisales", Context.MODE_PRIVATE).getInt("id_usuario", -1);
        if (id == -1){
            Toast.makeText(getContext(), "Error al buscar el usuario", Toast.LENGTH_SHORT).show();
        }else{
             dato = db.mostrarDatosUsuario(id).get(0);
            binding.idPerfil.setText(""+dato.getId());
            binding.nombrePerfil.setText(dato.getColumna1());
            binding.correoPerfil.setText(dato.getColumna2());
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        if (db == null){
            db = new DB(this.getContext());
        }

        getPerfil();
        super.onResume();
    }

    private void sincronizacion(){
        JSONArray jsonArray =new JSONArray();
        //for (int i =0; i<dato.size();i++){
            JSONObject jsonObject = new JSONObject();
            try{

                jsonObject.put("id", dato.getId());
                jsonObject.put("nombre",dato.getColumna1());
                jsonObject.put("correo",dato.getColumna2());
                jsonObject.put("password",dato.getColumna3());

            }catch(Exception e){
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        //}
        JSONObject json = new JSONObject();
        try{
            json.put("Usuario",jsonArray);
        }catch (Exception e){
            e.printStackTrace();
        }
        String jsonStr = json.toString();
        System.out.println(jsonStr);
        registarUsuario(jsonStr);
    }

    private void registarUsuario(final String jsonStr){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = "http://192.168.1.68/android/insertarUs.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("OK")){
                            Toast.makeText(getContext(), "SE SINCRONIZO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                return jsonStr.getBytes();
            }
            public String getBodyContentType() {
                return "application/json";
            }
        };
        requestQueue.add(stringRequest);
    }
    private View.OnClickListener sincro = v -> {
        sincronizacion();
    };
}