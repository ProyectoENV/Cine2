package com.example.proyectocine.Objetos;

import com.google.gson.annotations.SerializedName;

public class Pelicula {
    private String id;
    @SerializedName("Title")
    private String Titulo;
    @SerializedName("Director")
    private String Director;
    @SerializedName("Plot")
    private String Sinopsis;
    @SerializedName("Genre")
    private String Genero;
    @SerializedName("Poster")
    private String Imagen;
    @SerializedName("Released")
    private int Año;
    public Pelicula( String nombre, String director, String sinopsis, String genero, String imagen, int año) {
        Titulo = nombre;
        Director = director;
        Sinopsis = sinopsis;
        this.Genero = genero;
        Imagen = imagen;
        Año = año;
    }
    public Pelicula( String id,String nombre, String director, String sinopsis, String genero, String imagen, int año) {
        this.id =id;
        Titulo = nombre;
        Director = director;
        Sinopsis = sinopsis;
        this.Genero = genero;
        Imagen = imagen;
        Año = año;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return Titulo;
    }

    public String getDirector() {
        return Director;
    }

    public String getSinopsis() {
        return Sinopsis;
    }

    public String getGenero() {
        return Genero;
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
