package com.example.proyectocine.Objetos;

public class Sala {
    private String idSala;
    private String Numerosala;
    private int fila;
    private int asiento;
    private String Hora;
    private String Dia;

    public Sala(String idSala, String hora, String numerosala, String dia) {
        this.idSala = idSala;
        Hora = hora;
        Numerosala = numerosala;
        this.Dia = dia;
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

    public String getDia() {
        return Dia;
    }
}
