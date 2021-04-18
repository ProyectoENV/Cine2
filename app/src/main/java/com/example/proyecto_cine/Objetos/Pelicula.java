package com.example.proyecto_cine.Objetos;

import com.google.gson.annotations.SerializedName;

public class Pelicula {
    private String id;
    @SerializedName("Title")
    private String Nombre;
    @SerializedName("Director")
    private String Director;
    @SerializedName("Plot")
    private String Sinopsis;
    @SerializedName("Genre")
    private String genero;
    @SerializedName("Poster")
    private String Imagen;
    @SerializedName("Released")
    private int Año;

    public Pelicula( String nombre, String director, String sinopsis, String genero, String imagen, int año) {
        Nombre = nombre;
        Director = director;
        Sinopsis = sinopsis;
        this.genero = genero;
        Imagen = imagen;
        Año = año;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getDirector() {
        return Director;
    }

    public String getSinopsis() {
        return Sinopsis;
    }

    public String getGenero() {
        return genero;
    }

    public String getImagen() {
        return Imagen;
    }

    public int getAño() {
        return Año;
    }

    public void setId(String id) {
        this.id = id;
    }
}
