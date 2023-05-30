package com.example.jhaisales;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class Carrito {
        private Set<String> productos;
        SharedPreferences preferences;

    public Carrito(Context context){//Conjunto de productos almacenados en los preferences.

            preferences = context.getSharedPreferences("jhaisales", Context.MODE_PRIVATE);
            productos = preferences.getStringSet("carrito", null);

        if ( productos == null){

            productos = new HashSet<String>();

         }
        }

        //Permite agregar un producto al carrito actualizando el conjunto de productos almacenados en las preferencias.
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

        //Elimina todos los productos almacenados en el carrito de compras eliminando el valor asociado a la clave "carrito" en las preferencias.
        public void vaciar(){
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("carrito");
            editor.commit(); editor.apply();
        }
}
