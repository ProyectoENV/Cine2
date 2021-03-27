package com.example.proyecto_cine.Objetos;

public class Sala {
    private int idSala;
    private int numerosala;
    private int fila;
    private int asiento;

    public Sala(int numerosala, int fila, int asiento) {
        this.numerosala = numerosala;
        this.fila = fila;
        this.asiento = asiento;
    }

    public int getIdSala() {
        return idSala;
    }

    public int getNumerosala() {
        return numerosala;
    }

    public void setNumerosala(int numerosala) {
        this.numerosala = numerosala;
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
