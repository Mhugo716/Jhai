package com.example.jhaisales.db;

public class Datos {
    private int id;

    private int cantidad;
    private String columna1;
    private String columna2;
    private String columna3;
    private String columna4;
    private String columna5;
    private String columna6;
    private String columna7;

    private byte[] imagen;

    public Datos() {
        this.id = id;
        this.columna1 = columna1;
        this.columna2 = columna2;
        this.columna3 = columna3;
        this.columna4 = columna4;
        this.columna5 = columna5;
        this.columna6 = columna6;
        this.columna7 = columna7;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColumna1() {
        return columna1;
    }

    public void setColumna1(String columna1) {
        this.columna1 = columna1;
    }

    public String getColumna2() {
        return columna2;
    }

    public void setColumna2(String columna2) {
        this.columna2 = columna2;
    }

    public String getColumna3() {
        return columna3;
    }

    public void setColumna3(String columna3) {
        this.columna3 = columna3;
    }

    public String getColumna4() {
        return columna4;
    }

    public void setColumna4(String columna4) {
        this.columna4 = columna4;
    }

    public String getColumna5() {
        return columna5;
    }

    public void setColumna5(String columna5) {
        this.columna5 = columna5;
    }

    public String getColumna6() {
        return columna6;
    }

    public void setColumna6(String columna6) {
        this.columna6 = columna6;
    }

    public String getColumna7() {
        return columna7;
    }

    public void setColumna7(String columna7) {
        this.columna7 = columna7;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
