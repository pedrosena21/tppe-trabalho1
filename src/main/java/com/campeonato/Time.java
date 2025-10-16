package com.campeonato;

public class Time {
    private String nome;
    private String estadio;

    private int pontos;

    private int vitorias;
    private int empates;
    private int derrotas;
    
    private int golsMarcados;
    private int golsSofridos;
    private int saldoGols;

    private int cartoesAmarelos;
    private int cartoesVermelhos;

    public Time(String nome, String estadio) {
        this.nome = nome;
        this.estadio = estadio;
        this.pontos = 0;
        this.vitorias = 0;
        this.empates = 0;
        this.derrotas = 0;
        this.golsMarcados = 0;
        this.golsSofridos = 0;
        this.saldoGols = 0;
        this.cartoesAmarelos = 0;
        this.cartoesVermelhos = 0;
    }

    public void atualizarEstatisticas(int golsMarcados, int golsSofridos, int cartoesAmarelos, int cartoesVermelhos) {
        this.golsMarcados += golsMarcados;
        this.golsSofridos += golsSofridos;
        this.saldoGols = this.golsMarcados - this.golsSofridos;
        this.cartoesAmarelos += cartoesAmarelos;
        this.cartoesVermelhos += cartoesVermelhos;

        if (golsMarcados > golsSofridos) {
            this.vitorias += 1;
            this.pontos += 3;
        } else if (golsMarcados == golsSofridos) {
            this.empates += 1;
            this.pontos += 1;
        } else {
            this.derrotas += 1;
        }
    }

    public String getNome() {
        return nome;
    }
    public String getEstadio() {
        return estadio;
    }
    public int getPontos() {
        return pontos;
    }
    public int getVitorias() {
        return vitorias;
    }

    public int getEmpates() {
        return empates;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public int getGolsMarcados() {
        return golsMarcados;
    }

    public int getGolsSofridos() {
        return golsSofridos;
    }

    public int getSaldoGols() {
        return saldoGols;
    }

    public int getCartoesAmarelos() {
        return cartoesAmarelos;
    }

    public int getCartoesVermelhos() {
        return cartoesVermelhos;
    }

    @Override
    public String toString() {
        return String.format("%-20s - Pts: %2d | V: %2d | E: %2d | D: %2d | GM: %3d | GS: %3d | SG: %3d | CA: %2d | CV: %2d",
                nome, pontos, vitorias, empates, derrotas, golsMarcados, golsSofridos, saldoGols, cartoesAmarelos, cartoesVermelhos);
    }
}