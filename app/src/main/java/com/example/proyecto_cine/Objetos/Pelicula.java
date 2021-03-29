package com.example.proyecto_cine.Objetos;

public class Pelicula {
    private int id ;
    private String Nombre;
    private String[] Actores;
    private String Director;
    private String Sinopsis;
    private Genero genero;

    public Pelicula(String nombre, String[] actores, String director, String sinopsis, Genero genero) {
        Nombre = nombre;
        Actores = actores;
        Director = director;
        Sinopsis = sinopsis;
        this.genero = genero;
    }

    public int getId() {
        return id;
    }


    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String[] getActores() {
        return Actores;
    }

    public void setActores(String[] actores) {
        Actores = actores;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getSinopsis() {
        return Sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        Sinopsis = sinopsis;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
}
