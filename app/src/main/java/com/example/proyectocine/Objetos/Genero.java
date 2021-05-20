package com.example.proyectocine.Objetos;

public class Genero {
    private int Idgenero;
    private String Nombre;
    private String Descripcion;

    public Genero(String nombre, String descripcion) {
        Nombre = nombre;
        Descripcion = descripcion;
    }

    public int getIdgenero() {
        return Idgenero;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
