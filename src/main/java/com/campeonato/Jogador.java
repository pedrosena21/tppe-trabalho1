package com.campeonato;

public class Jogador {

    private Time time;
    private String nome;
    private int camisa;


    public Jogador(Time time, String nome, int camisa) {
        this.time = time;
        this.nome = nome;
        this.camisa = camisa;
    }

    public int getCamisa() {
        return camisa;
    }

    public Time getTime() {
        return time;
    }

    public String getNome() {
        return nome;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
