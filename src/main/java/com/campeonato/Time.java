package com.campeonato;

import java.util.ArrayList;
import java.util.List;

import com.campeonato.exception.JogadorNaoEncontradorException;

public class Time {
    private List<Jogador> jogadores;
    private String estadio;
    private String nome;
    private EstatisticasTime estatisticas;

    public Time(String nome, String estadio) {
        this.nome = nome;
        this.estadio = estadio;
        this.jogadores = new ArrayList<>();
        this.estatisticas = new EstatisticasTime();
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

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public String getNome() {
        return nome;
    }

    public String getEstadio() {
        return estadio;
    }

    // Métodos delegados para EstatisticasTime
    public void atualizarEstatisticas(int golsMarcados, int golsSofridos, 
                                     int cartoesAmarelos, int cartoesVermelhos) {
        this.estatisticas.atualizarEstatisticas(golsMarcados, golsSofridos, 
                                               cartoesAmarelos, cartoesVermelhos);
    }

    public int getPontos() {
        return estatisticas.getPontos();
    }

    public int getVitorias() {
        return estatisticas.getVitorias();
    }

    public int getEmpates() {
        return estatisticas.getEmpates();
    }

    public int getDerrotas() {
        return estatisticas.getDerrotas();
    }

    public int getGolsMarcados() {
        return estatisticas.getGolsMarcados();
    }

    public int getGolsSofridos() {
        return estatisticas.getGolsSofridos();
    }

    public int getSaldoGols() {
        return estatisticas.getSaldoGols();
    }

    public int getCartoesAmarelos() {
        return estatisticas.getCartoesAmarelos();
    }

    public int getCartoesVermelhos() {
        return estatisticas.getCartoesVermelhos();
    }

    public void adicionarCartaoVermelho() {
        estatisticas.adicionarCartaoVermelho();
    }

    public void adicionarCartaoAmarelo() {
        estatisticas.adicionarCartaoAmarelo();
    }

    public void adicionarGolMarcado() {
        estatisticas.adicionarGolMarcado();
    }

    public void adicionarGolSofrido() {
        estatisticas.adicionarGolSofrido();
    }

    public void anularGolMarcado() {
        estatisticas.anularGolMarcado();
    }

    public void anularGolSofrido() {
        estatisticas.anularGolSofrido();
    }

    public EstatisticasTime getEstatisticas() {
        return estatisticas;
    }

    @Override
    public String toString() {
        return String.format("%-20s - %s", nome, estatisticas.toString());
    }
}