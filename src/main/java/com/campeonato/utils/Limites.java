package com.campeonato.utils;

public enum Limites {
    MAXIMO_TIMES_CAMPEONATO(20),
    MAXIMO_CONFRONTOS_POR_RODADA(5);

    private int valor;

    Limites(int valor) {
        this.valor = valor;
    }

    public int get() {
        return this.valor;
    }
}
