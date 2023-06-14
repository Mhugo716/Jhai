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
import com.example.jhaisales.databinding.FragmentPedidosBinding;

import com.example.jhaisales.db.AdapterPD;
import com.example.jhaisales.db.DB;
import com.example.jhaisales.db.Datos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class pedidos extends Fragment {
    private FragmentPedidosBinding binding;
    private DB db;
    private ArrayList<Datos> datos;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPedidosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new DB(this.getContext());

        datos = db.mostrarPedidos();

        recyclerView = root.findViewById(R.id.recyclerViewPedido);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        AdapterPD adapterPD = new AdapterPD(db.mostrarPedidos(), getContext());
        recyclerView.setAdapter(adapterPD);

        binding.btnSincro.setOnClickListener(sincro);

        return root;

    }

    private void sincronizacion(){
        JSONArray jsonArray =new JSONArray();
        for (int i =0; i<datos.size();i++){
            JSONObject jsonObject = new JSONObject();
            try{

                jsonObject.put("idPartida",datos.get(i).getId());
                jsonObject.put("nombreProducto",datos.get(i).getColumna1());
                jsonObject.put("numeroPedido",datos.get(i).getColumna2());
                jsonObject.put("idProducto",datos.get(i).getColumna3());
                jsonObject.put("categoria",datos.get(i).getColumna4());
                jsonObject.put("precio",datos.get(i).getColumna5());
                jsonObject.put("imgProducto",datos.get(i).getImagen());

            }catch(Exception e){
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }
        JSONObject json = new JSONObject();
        try{
            json.put("Partidas",jsonArray);
        }catch (Exception e){
            e.printStackTrace();
        }
        String jsonStr = json.toString();
        System.out.println(jsonStr);
        registarCliente(jsonStr);
    }

    private void registarCliente(final String jsonStr){

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = "https://catalogos.estudiasistemas.com/insertarPD.php";
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