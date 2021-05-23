package com.example.proyectocine.Objetos;

public class Factura {
    private String id_factura;
    private String id_user;
    private String id_pelicula;
    private String id_cine;
    private String id_sala;
    private String numero_entradas;
    private String hora;
    private String dia;
    private String fecha_compra;

    public Factura(String id_user, String id_pelicula, String id_cine, String id_sala, String numero_entradas, String hora, String dia, String fecha_compra) {
        this.id_user = id_user;
        this.id_pelicula = id_pelicula;
        this.id_cine = id_cine;
        this.id_sala = id_sala;
        this.numero_entradas = numero_entradas;
        this.hora = hora;
        this.dia = dia;
        this.fecha_compra = fecha_compra;
    }

    public String getId_user() {
        return id_user;
    }

    public String getId_pelicula() {
        return id_pelicula;
    }

    public String getId_cine() {
        return id_cine;
    }

    public String getId_sala() {
        return id_sala;
    }

    public String getNumero_entradas() {
        return numero_entradas;
    }

    public String getHora() {
        return hora;
    }

    public String getDia() {
        return dia;
    }

    public String getFecha_compra() {
        return fecha_compra;
    }
}
