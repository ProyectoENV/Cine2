package com.example.proyecto_cine.Objetos;

public class Sala {
    private String idSala;
    private String Numerosala;
    private int fila;
    private int asiento;
    private int Asientos_ocupados;
    private int Capacidad_asientos;
    private String Hora;
    private String ID_CINE;

    public Sala(String idSala, String hora, String numerosala) {
        this.idSala = idSala;
        Hora = hora;
        Numerosala = numerosala;
    }

    public String getID_CINE() {
        return ID_CINE;
    }

    public String getIdSala() {
        return idSala;
    }

    public String getNumerosala() {
        return Numerosala;
    }

    public void setNumerosala(String numerosala) {
        Numerosala = numerosala;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getAsiento() {
        return asiento;
    }

    public void setAsiento(int asiento) {
        this.asiento = asiento;
    }
}
