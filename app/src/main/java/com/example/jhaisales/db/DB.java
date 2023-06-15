package com.example.jhaisales.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 23;
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
                "precio INTEGER NOT NULL," +
                "descripcion TEXT NOT NULL," +
                "categoria TEXT NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PARTIDAS + "(" +
                "idPartida INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombreProducto TEXT NOT NULL, " +
                "numeroPedido TEXT NOT NULL," +
                "idProducto INTEGER NOT NULL," +
                "categoria TEXT NOT NULL," +
                "precio DOUBLE NOT NULL," +
                "imgProducto BLOB NOT NULL," +
                "FOREIGN KEY(nombreProducto) REFERENCES " +
                TABLE_PARTIDAS + "(nombreProducto)," +
                "FOREIGN KEY(idProducto) REFERENCES " +
                TABLE_PARTIDAS + "(idProducto), " +
                "FOREIGN KEY(precio) REFERENCES " +
                TABLE_PARTIDAS + "(precio)," +
                "FOREIGN KEY(imgProducto) REFERENCES " +
                TABLE_PARTIDAS + "(imgProducto))");

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

    public boolean insertarPedido(String nombreProduco, int numeroPedido, int idProducto, String categoria, String precio,  byte[] imagen){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("nombreProducto", nombreProduco);
        cv.put("numeroPedido", numeroPedido);
        cv.put("idProducto", idProducto);
        cv.put("categoria", categoria);
        cv.put("precio", precio);
        cv.put("imgProducto", imagen);

        long result = sqLiteDatabase.insert(TABLE_PARTIDAS, null, cv);

        return (result != -1);
    }

    public int checkpass(String password, String nombre) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from usuario Where nombre=? and password = ?", new String[]{nombre.trim(), password.trim()});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getInt(0);
        } else {
            return -1;
        }
    }

    public boolean usuarioExiste(String nombre){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from usuario Where nombre=?", new String[]{nombre});

        boolean existe = (cursor.getCount() > 0);

        cursor.close();
        database.close();

        return existe;
    }

    public boolean contraseñaError(String password){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from usuario Where password=?", new String[]{password});

        boolean contraseñaIncorrecta = (cursor.getCount() == 0);

        cursor.close();
        database.close();

        return contraseñaIncorrecta;

    }


    public boolean usuarioError(String nombre) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM usuario WHERE nombre=?", new String[]{nombre});

        boolean usuarioError = (cursor.getCount() == 0);

        cursor.close();
        database.close();

        return usuarioError;

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

    public ArrayList mostrarDatosCliente(){

        SQLiteDatabase database = this.getWritableDatabase();

        ArrayList<Datos> listadate = new ArrayList<>();
        Datos dato = null;
        Cursor cursor = null;
        cursor = database.rawQuery("SELECT * FROM " + TABLE_CLIENTE, null);

        if (cursor.moveToFirst()) {
            do {

                dato = new Datos();
                dato.setId(cursor.getInt(0));
                dato.setColumna1(cursor.getString(1));
                dato.setColumna2(cursor.getString(2));
                dato.setColumna3(cursor.getString(3));
                dato.setColumna4(cursor.getString(4));

                listadate.add(dato);

            }while (cursor.moveToNext());
        }

        cursor.close();
        return listadate;

    }

    public ArrayList<Datos> mostrarDatosUsuario(int id){
        SQLiteDatabase database = this.getWritableDatabase();

        ArrayList<Datos> listadate = new ArrayList<>();
        Datos dato = null;
        Cursor cursor = null;
        cursor = database.rawQuery("SELECT * FROM " + TABLE_USUARIOS + " WHERE id= "+ id , null);

        if (cursor.moveToFirst()){
            do{
                dato = new Datos();
                dato.setId(cursor.getInt(0));
                dato.setColumna1(cursor.getString(1));
                dato.setColumna2(cursor.getString(2));
                dato.setColumna3(cursor.getString(3));

                listadate.add(dato);
            }while (cursor.moveToNext());

        }
        cursor.close();
        return listadate;
    }

    public ArrayList<Datos> mostrarPedidos() {
        SQLiteDatabase database = this.getWritableDatabase();

        ArrayList<Datos> listadatos = new ArrayList<>();
        Datos datos = null;
        Cursor cursor = null;
        cursor = database.rawQuery("SELECT * FROM " + TABLE_PARTIDAS, null);
        if (cursor.moveToFirst()) {
            do {
                datos = new Datos();
                datos.setId(cursor.getInt(0));
                datos.setColumna1(cursor.getString(1));
                datos.setColumna2(cursor.getString(2));
                datos.setColumna3(cursor.getString(3));
                datos.setColumna4(cursor.getString(4));
                datos.setColumna5(cursor.getString(5));
                datos.setImagen(cursor.getBlob(6));

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

    public Datos mostrarCliente(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        Datos data = null;
        Cursor cursor;
        cursor = database.rawQuery("SELECT * FROM " + TABLE_CLIENTE + " WHERE idCliente= " + id + " LIMIT 1", null);

        if (cursor.moveToNext()) {
            data = new Datos();
            data.setId(cursor.getInt(0));
            data.setColumna1(cursor.getString(1));
            data.setColumna2(cursor.getString(2));
            data.setColumna3(cursor.getString(3));
            data.setColumna4(cursor.getString(4));
        }
        cursor.close();
        return data;
    }

    public Datos mostrarUsuario(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        Datos date = null;
        Cursor cursor;
        cursor = database.rawQuery("SELECT * FROM " + TABLE_USUARIOS + " WHERE id= " + id + " LIMIT 1", null);

        if (cursor.moveToNext()){
            date = new Datos();
            date.setId(cursor.getInt(0));
            date.setColumna1(cursor.getString(1));
            date.setColumna2(cursor.getString(2));
            date.setColumna3(cursor.getString(3));
        }
        cursor.close();
        return date;

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

    public boolean modificarDatosCliente(int id, String nombreCliente, String direccion, String telefono, String referencias){
        SQLiteDatabase dataB = getWritableDatabase();
        boolean listo;
        try{
            dataB.execSQL(" UPDATE " + TABLE_CLIENTE + " SET nombreCliente ='" + nombreCliente + "', direccion = '" + direccion + "',telefono = '" + telefono + "',referencias = '" + referencias + " 'WHERE idCliente = '" + id + "'");
            listo = true;
        }catch (Exception ex){
            ex.toString();
            listo = false;
        }finally {
            dataB.close();
        }
        return listo;
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

    public boolean borrarCliente(int id){
        boolean correct = false;
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.execSQL(" DELETE FROM " + TABLE_CLIENTE +  " WHERE idCliente= '" + id + "'");
            correct = true;
        }catch (Exception ex){
            ex.toString();
            correct = false;
        }finally {
            db.close();
        }
        return correct;
    }


    public ArrayList<Datos> mostrarAvon() {
        SQLiteDatabase date = this.getWritableDatabase();

        ArrayList<Datos> listdatos = new ArrayList<>();
        Datos datos = null;
        Cursor cursor = null;
        cursor = date.rawQuery("SELECT * FROM " + TABLE_PRODUCTOS + " WHERE marca LIKE ?", new String[]{"%avon%"});
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
        cursor = dato.rawQuery("SELECT * FROM " + TABLE_PRODUCTOS + " WHERE marca LIKE ?", new String[]{"%andrea%"});
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
        cursor = dat.rawQuery("SELECT * FROM " + TABLE_PRODUCTOS + " WHERE marca LIKE ?", new String[]{"%betteware%"});
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
        cursor = date.rawQuery("SELECT * FROM " + TABLE_PRODUCTOS + " WHERE marca LIKE ?", new String[]{"%fuller%"});
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
        cursor = date.rawQuery("SELECT * FROM " + TABLE_PRODUCTOS + " WHERE marca LIKE ?", new String[]{"%vianney%"});
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
        cursor = date.rawQuery("SELECT * FROM " + TABLE_PRODUCTOS + " WHERE marca LIKE ?", new String[]{"%ilusion%"});
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

    public boolean modificarUsuario(int id, String correo, String password) {
        SQLiteDatabase dataB = getWritableDatabase();
        boolean listo;
        try{
            dataB.execSQL(" UPDATE " + TABLE_USUARIOS + " SET  correo = '" + correo + "',password = '" + password +"' WHERE id = '" + id + "'");
            listo = true;
        }catch (Exception ex){
            ex.toString();
            listo = false;
        }finally {
            dataB.close();
        }
        return listo;
    }
}
