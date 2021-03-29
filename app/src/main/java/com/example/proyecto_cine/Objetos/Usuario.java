package com.example.proyecto_cine.Objetos;

public class Usuario {
    private int Idusuario;
    private String Nombre_usuario;
    private String Password;
    private String Imagen_usuario;
    private Genero[] Preferencias;
    //private Factura[] facturas;

    public Usuario(String nombre_usuario, String password, String imagen_usuario, Genero[] preferencias /*Factura[] facturas*/) {
        Nombre_usuario = nombre_usuario;
        Password = password;
        Imagen_usuario = imagen_usuario;
        Preferencias = preferencias;
        //this.facturas = facturas;
    }

    public int getIdusuario() {
        return Idusuario;
    }


    public String getNombre_usuario() {
        return Nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        Nombre_usuario = nombre_usuario;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getImagen_usuario() {
        return Imagen_usuario;
    }

    public void setImagen_usuario(String imagen_usuario) {
        Imagen_usuario = imagen_usuario;
    }

    public Genero[] getPreferencias() {
        return Preferencias;
    }

    public void setPreferencias(Genero[] preferencias) {
        Preferencias = preferencias;
    }

    /*public Factura[] getFacturas() {
        return facturas;
    }

    public void setFacturas(Factura[] facturas) {
        this.facturas = facturas;
    }*/
}
