package com.campeonato;

import java.util.ArrayList;
import java.util.List;

import com.campeonato.exception.JogadorNaoEncontradorException;

public class Time {
    private List<Jogador> jogadores;
    private String estadio;
    private String nome;
    
    public Time(String nome, String estadio) {
        this.estadio = estadio;
        this.nome = nome;
        this.jogadores = new ArrayList<>();
    }

    public Jogador getJogador(int camisa) throws JogadorNaoEncontradorException {
        for (var jogador : jogadores) {
            if (jogador.getCamisa() == camisa) {
                return jogador;
            }
        }

        throw new JogadorNaoEncontradorException();
    }
    
    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void adicionarJogador(Jogador jogador) {
        this.jogadores.add(jogador);
        jogador.setTime(this);
    }

    public void removerJogador(Jogador jogador) {
        this.jogadores.remove(jogador);
        jogador.setTime(null);
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.equals(this.nome);
    }
}
