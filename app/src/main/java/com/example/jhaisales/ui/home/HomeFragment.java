package com.example.jhaisales.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import com.example.jhaisales.databinding.FragmentHomeBinding;
import com.example.jhaisales.db.AdapterP;
import com.example.jhaisales.db.DB;
import com.example.jhaisales.db.Datos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    DB db;
    private AdapterP productos;
    private List<Datos> datos;
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        db = new DB(this.getContext());

        datos = new ArrayList<>(db.mostrartodoProductos());

        //Relacion con el recyclerView de la vista
        recyclerView = root.findViewById(R.id.rcProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //Manda a llamar al Adapter para visualizar los prodcutos en la pantalla con la funcion mostrarProductos
        AdapterP adapter = new AdapterP(db.mostrartodoProductos(),getContext());
        recyclerView.setAdapter(adapter);

        binding.btnSincronizar.setOnClickListener(sincro);

        return root;
    }
    private void sincronizacion(){
        JSONArray jsonArray =new JSONArray();
        for (int i =0; i<datos.size();i++){
            JSONObject jsonObject = new JSONObject();
            try{
                jsonObject.put("idProducto",datos.get(i).getId());
                jsonObject.put("nombreProducto",datos.get(i).getColumna1());
                jsonObject.put("imgProducto",datos.get(i).getImagen());
                jsonObject.put("marca",datos.get(i).getColumna2());
                jsonObject.put("precio",datos.get(i).getColumna3());
                jsonObject.put("descripcion",datos.get(i).getColumna4());
                jsonObject.put("categoria",datos.get(i).getColumna5());

            }catch(Exception e){
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }
        JSONObject json = new JSONObject();
        try{
            json.put("Productos",jsonArray);
        }catch (Exception e){
            e.printStackTrace();
        }
        String jsonStr = json.toString();
        System.out.println(jsonStr);
        registarProducto(jsonStr);
    }
    private void registarProducto(final String jsonStr){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = "http://192.168.1.69/android/insertarP.php";
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}