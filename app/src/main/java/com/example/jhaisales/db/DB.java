package com.example.jhaisales.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 12;
    private static final String DATABASE_NOMBRE = "jhai.db";
    private static final String TABLE_USUARIOS = "usuario";

    private static final String TABLE_PRODUCTOS = "productos";

    private static final String TABLE_PARTIDAS = "partidas";

    private static final String TABLE_PEDIDO = "pedidos";

    private static final String TABLE_CLIENTE = "cliente";

    public DB(Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USUARIOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL," +
                "correo TEXT NOT NULL," +
                "password TEXT NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCTOS + "(" +
                "idProducto integer PRIMARY KEY AUTOINCREMENT, " +
                "nombreProducto TEXT NOT NULL," +
                "imgProducto BLOB NOT NULL," +
                "marca TEXT NOT NULL," +
                "precio DOUBLE NOT NULL," +
                "descripcion TEXT NOT NULL," +
                "categoria TEXT NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PARTIDAS + "(" +
                "idPartida INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombreProducto TEXT NOT NULL, " +
                "numeroPedido INTEGER NOT NULL, " +
                "idProducto INTEGER NOT NULL," +
                "categoria TEXT NOT NULL," +
                "FOREIGN KEY('nombreProducto') REFERENCES " +
                TABLE_PARTIDAS + "('nombreProducto')," +
                "FOREIGN KEY('idProducto') REFERENCES " +
                TABLE_PARTIDAS + "('idProducto'))");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CLIENTE + "(" +
                "idCliente integer PRIMARY KEY AUTOINCREMENT, " +
                "nombreCliente TEXT NOT NULL," +
                "direccion TEXT NOT NULL," +
                "telefono TEXT NOT NULL," +
                "referencias TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTIDAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTE);

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insertUsuario(String nombre, String correo, String password) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", nombre);
        contentValues.put("correo", correo);
        contentValues.put("password", password);

        long resultados = sqLiteDatabase.insert(TABLE_USUARIOS, null, contentValues);

        if (resultados == 1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertCliente(String nombreCliente, String direccion, String telefono, String referencias){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nombreCliente", nombreCliente);
        cv.put("direccion", direccion);
        cv.put("telefono", telefono);
        cv.put("referencias", referencias);

        long result = sqLiteDatabase.insert(TABLE_CLIENTE, null, cv);

        if (result ==1){
            return false;
        }else{
            return true;
        }
    }

    public boolean checkpass(String password, String nombre) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from usuario Where nombre=? and password = ?", new String[]{nombre, password});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean insertarProducto(String nombre, String marca, String precio, String descripcion, String categoria, byte[] imagen) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nombreProducto", nombre);
        cv.put("imgProducto", imagen);
        cv.put("marca", marca);
        cv.put("precio", precio);
        cv.put("descripcion", descripcion);
        cv.put("categoria", categoria);
        long result = database.insert(TABLE_PRODUCTOS, null, cv);
        close();
        if (result == 1) {
            return false;
        } else {
            return true;
        }
    }


    public ArrayList<Datos> mostrartodoProductos() {
        SQLiteDatabase database = this.getWritableDatabase();

        ArrayList<Datos> listadatos = new ArrayList<>();
        Datos datos = null;
        Cursor cursor = null;
        cursor = database.rawQuery("SELECT * FROM " + TABLE_PRODUCTOS, null);
        if (cursor.moveToFirst()) {
            do {
                datos = new Datos();
                datos.setId(cursor.getInt(0));
                datos.setColumna1(cursor.getString(1));
                datos.setImagen(cursor.getBlob(2));
                datos.setColumna2(cursor.getString(3));
                datos.setColumna3(cursor.getString(4));
                datos.setColumna4(cursor.getString(5));
                datos.setColumna5(cursor.getString(6));

                listadatos.add(datos);
            } while (cursor.moveToNext());

        }
        cursor.close();
        return listadatos;
    }

    public Datos mostrarproducto(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        Datos datos = null;
        Cursor cursor;
        cursor = database.rawQuery("SELECT * FROM " + TABLE_PRODUCTOS + " WHERE idProducto= " + id + " LIMIT 1", null);
        if (cursor.moveToNext()) {
            datos = new Datos();
            datos.setId(cursor.getInt(0));
            datos.setColumna1(cursor.getString(1));
            datos.setImagen(cursor.getBlob(2));
            datos.setColumna2(cursor.getString(3));
            datos.setColumna3(cursor.getString(4));
            datos.setColumna4(cursor.getString(5));
            datos.setColumna5(cursor.getString(6));
        }
        cursor.close();
        return datos;
    }

    public boolean modificarproducto(int id, String nombreProducto, byte[] imagen, String marca, Double precio, String descripcion, String categoria) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues img = new ContentValues();
        img.put("imgProducto", imagen);
        boolean correcto;
        try {
            db.execSQL("UPDATE " + TABLE_PRODUCTOS + " SET nombreProducto = '" + nombreProducto + "', marca = '" + marca + "',precio ='" + precio + "',descripcion ='" + descripcion + "',categoria ='" + categoria + "'WHERE idProducto='" + id + "'");
            db.update(TABLE_PRODUCTOS, img, "idProducto=" + id, null);
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }
        return correcto;
    }

    public boolean borrarProducto(int id) {
        boolean correcto = false;
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL("DELETE FROM " + TABLE_PRODUCTOS + " WHERE idProducto = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }
        return correcto;
    }


    public ArrayList<Datos> mostrarAvon() {
        SQLiteDatabase date = this.getWritableDatabase();

        ArrayList<Datos> listdatos = new ArrayList<>();
        Datos datos = null;
        Cursor cursor = null;
        cursor = date.rawQuery("SELECT * FROM " + TABLE_PRODUCTOS + " WHERE marca = ?", new String[]{"avon"});
        if (cursor.moveToFirst()) {
            do {
                datos = new Datos();
                datos.setId(cursor.getInt(0));
                datos.setColumna1(cursor.getString(1));
                datos.setImagen(cursor.getBlob(2));
                datos.setColumna2(cursor.getString(3));
                datos.setColumna3(cursor.getString(4));
                datos.setColumna4(cursor.getString(5));
                datos.setColumna5(cursor.getString(6));

                listdatos.add(datos);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listdatos;

    }

    public ArrayList<Datos> mostrarAndrea() {
        SQLiteDatabase dato = this.getWritableDatabase();

        ArrayList<Datos> lisdatos = new ArrayList<>();
        Datos datos = null;
        Cursor cursor = null;
        cursor = dato.rawQuery("SELECT * FROM " + TABLE_PRODUCTOS + " WHERE marca = ?", new String[]{"andrea"});
        if (cursor.moveToFirst()) {
            do {
                datos = new Datos();
                datos.setId(cursor.getInt(0));
                datos.setColumna1(cursor.getString(1));
                datos.setImagen(cursor.getBlob(2));
                datos.setColumna2(cursor.getString(3));
                datos.setColumna3(cursor.getString(4));
                datos.setColumna4(cursor.getString(5));
                datos.setColumna5(cursor.getString(6));

                lisdatos.add(datos);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lisdatos;

    }

    public ArrayList<Datos> mostrarBetteware() {
        SQLiteDatabase dat = this.getWritableDatabase();

        ArrayList<Datos> lidatos = new ArrayList<>();
        Datos datos = null;
        Cursor cursor = null;
        cursor = dat.rawQuery("SELECT * FROM " + TABLE_PRODUCTOS + " WHERE marca = ?", new String[]{"betteware"});
        if (cursor.moveToFirst()) {
            do {
                datos = new Datos();
                datos.setId(cursor.getInt(0));
                datos.setColumna1(cursor.getString(1));
                datos.setImagen(cursor.getBlob(2));
                datos.setColumna2(cursor.getString(3));
                datos.setColumna3(cursor.getString(4));
                datos.setColumna4(cursor.getString(5));
                datos.setColumna5(cursor.getString(6));

                lidatos.add(datos);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lidatos;

    }

    public ArrayList<Datos> mostrarFuller() {
        SQLiteDatabase date = this.getWritableDatabase();

        ArrayList<Datos> lisdatos = new ArrayList<>();
        Datos datos = null;
        Cursor cursor = null;
        cursor = date.rawQuery("SELECT * FROM " + TABLE_PRODUCTOS + " WHERE marca = ?", new String[]{"fuller"});
        if (cursor.moveToFirst()) {
            do {
                datos = new Datos();
                datos.setId(cursor.getInt(0));
                datos.setColumna1(cursor.getString(1));
                datos.setImagen(cursor.getBlob(2));
                datos.setColumna2(cursor.getString(3));
                datos.setColumna3(cursor.getString(4));
                datos.setColumna4(cursor.getString(5));
                datos.setColumna5(cursor.getString(6));

                lisdatos.add(datos);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lisdatos;

    }

    public ArrayList<Datos> mostrarVianney() {
        SQLiteDatabase date = this.getWritableDatabase();

        ArrayList<Datos> lisdatos = new ArrayList<>();
        Datos datos = null;
        Cursor cursor = null;
        cursor = date.rawQuery("SELECT * FROM " + TABLE_PRODUCTOS + " WHERE marca = ?", new String[]{"vianney"});
        if (cursor.moveToFirst()) {
            do {
                datos = new Datos();
                datos.setId(cursor.getInt(0));
                datos.setColumna1(cursor.getString(1));
                datos.setImagen(cursor.getBlob(2));
                datos.setColumna2(cursor.getString(3));
                datos.setColumna3(cursor.getString(4));
                datos.setColumna4(cursor.getString(5));
                datos.setColumna5(cursor.getString(6));

                lisdatos.add(datos);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lisdatos;

    }

    public ArrayList<Datos> mostrarIlusion() {
        SQLiteDatabase date = this.getWritableDatabase();

        ArrayList<Datos> lisdatos = new ArrayList<>();
        Datos datos = null;
        Cursor cursor = null;
        cursor = date.rawQuery("SELECT * FROM " + TABLE_PRODUCTOS + " WHERE marca = ?", new String[]{"ilusion"});
        if (cursor.moveToFirst()) {
            do {
                datos = new Datos();
                datos.setId(cursor.getInt(0));
                datos.setColumna1(cursor.getString(1));
                datos.setImagen(cursor.getBlob(2));
                datos.setColumna2(cursor.getString(3));
                datos.setColumna3(cursor.getString(4));
                datos.setColumna4(cursor.getString(5));
                datos.setColumna5(cursor.getString(6));

                lisdatos.add(datos);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lisdatos;

    }

}
