package com.example.proyecto_cine.Objetos;

public class Cine {
    private int idCine;
    private String Nombre;
    private String Localizacion;
    private Pelicula[] Peliculas_Cartelera;
    private Sala[] salas_cine;

    public Cine(String nombre, String localizacion, Pelicula[] peliculas_Cartelera, Sala[] salas_cine) {
        Nombre = nombre;
        Localizacion = localizacion;
        Peliculas_Cartelera = peliculas_Cartelera;
        this.salas_cine = salas_cine;
    }

    public int getIdCine() {
        return idCine;
    }


    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getLocalizacion() {
        return Localizacion;
    }

    public void setLocalizacion(String localizacion) {
        Localizacion = localizacion;
    }

    public Pelicula[] getPeliculas_Cartelera() {
        return Peliculas_Cartelera;
    }

    public void setPeliculas_Cartelera(Pelicula[] peliculas_Cartelera) {
        Peliculas_Cartelera = peliculas_Cartelera;
    }

    public Sala[] getSalas_cine() {
        return salas_cine;
    }

    public void setSalas_cine(Sala[] salas_cine) {
        this.salas_cine = salas_cine;
    }
}
