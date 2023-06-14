package com.example.jhaisales;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jhaisales.databinding.FragmentListaClientesBinding;
import com.example.jhaisales.db.AdapterClient;
import com.example.jhaisales.db.AdapterP;
import com.example.jhaisales.db.DB;
import com.example.jhaisales.db.Datos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Lista_Clientes extends Fragment {

    private List<Datos> date;
    DB db;
    RecyclerView recyclerView;
    FragmentListaClientesBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentListaClientesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DB(this.getContext());

        date = new ArrayList<>(db.mostrarDatosCliente());


        recyclerView = root.findViewById(R.id.listaClientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        AdapterClient adapter = new AdapterClient(db.mostrarDatosCliente(),getContext());
        recyclerView.setAdapter(adapter);

        binding.btnSincronizacion.setOnClickListener(sincro);

        return root;
    }

    private void sincronizacion(){
        JSONArray jsonArray =new JSONArray();
        for (int i =0; i<date.size();i++){
            JSONObject jsonObject = new JSONObject();
            try{

                jsonObject.put("idCliente",date.get(i).getId());
                jsonObject.put("nombreCliente",date.get(i).getColumna1());
                jsonObject.put("direccion",date.get(i).getColumna2());
                jsonObject.put("telefono",date.get(i).getColumna3());
                jsonObject.put("referencias",date.get(i).getColumna4());


            }catch(Exception e){
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }
        JSONObject json = new JSONObject();
        try{
            json.put("Cliente",jsonArray);
        }catch (Exception e){
            e.printStackTrace();
        }
        String jsonStr = json.toString();
        System.out.println(jsonStr);
        registarCliente(jsonStr);
    }

    private void registarCliente(final String jsonStr){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = "https://catalogos.estudiasistemas.com/insertarCli.php";
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