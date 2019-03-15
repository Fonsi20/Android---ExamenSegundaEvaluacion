package com.example.f_alfonsofernandez.Objeto;

public class Compuestos {
    private String nombre;
    private String siglas;

    public Compuestos() {
    }

    public Compuestos(String nombre, String siglas) {
        this.nombre = nombre;
        this.siglas = siglas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }
}