package com.example.jhaisales;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class Carrito {
        private Set<String> productos;
        SharedPreferences preferences;

    public Carrito(Context context){

            preferences = context.getSharedPreferences("jhaisales", Context.MODE_PRIVATE);
            productos = preferences.getStringSet("carrito", null);
        if ( productos == null){
            productos = new HashSet<String>();
        }
        }

        public Set<String> getProductos(){
            return productos;
        }

        public void agregar(String id){

            SharedPreferences.Editor editor = preferences.edit();
            if(productos !=null){

                editor.remove("carrito");
                editor.commit();
                //productos = new HashSet<String>();

            }

            productos.add(id);
            editor.putStringSet("carrito", productos);
            editor.commit();

        }
}
