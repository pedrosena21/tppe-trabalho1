package com.campeonato;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Partida {
    
    private Time timeCasa;
    private Time timeFora;
    private LocalDateTime inicioPartida;
    private String estadio;

    private List<Jogador> cartoesVermelhos;
    private List<Jogador> cartoesAmarelos;
    private List<Gol> gols;
    private int placarTimeCasa = 0;
    private int placarTimeFora = 0;

    public Partida(){};

    public Partida(Time timecasa, Time timefora, LocalDateTime inicioPartida) {
        this.timeCasa = timecasa;
        this.timeFora = timefora;
        this.inicioPartida = inicioPartida;
        this.cartoesAmarelos = new ArrayList<>();
        this.cartoesVermelhos = new ArrayList<>();
        this.gols = new ArrayList<>();
        setEstadio();
    }

    private void setEstadio() {
        this.estadio = this.timeCasa.getEstadio();
    }

    public String getEstadio() {
        return this.estadio;
    }

    public Time getTimeCasa() {
        return timeCasa;
    }

    public Time getTimeFora() {
        return timeFora;
    }

    public LocalDateTime getInicioPartida() {
        return inicioPartida;
    }
    
    public List<Gol> getGols(Time fez) {
        return gols.stream().filter(g -> g.getFez() == fez).toList();
    }

    public List<Gol> getGols() {
        return gols;
    }

    public void inserirCartaoVermelho(Jogador jogador) {
        this.cartoesVermelhos.add(jogador);
    }

    public void inserirCartaoAmarelo(Jogador jogador) {
        this.cartoesAmarelos.add(jogador);
    }

    public List<Jogador> getCartoesVermelhos() {        
        return cartoesVermelhos;
    }

    public List<Jogador> getCartoesAmarelos() {        
        return cartoesAmarelos;
    }

    public List<Jogador> getCartoesVermelhos(Time time) {        
        return cartoesVermelhos.stream().filter(cartao -> cartao.getTime() == time).toList();
    }

    public List<Jogador> getCartoesAmarelos(Time time) {        
        return cartoesAmarelos.stream().filter(cartao -> cartao.getTime() == time).toList();
    }

    public void anularGol(Gol gol) {
        gol.anularGol();
    }

    public void marcarGol(Jogador jogador, int minutos, int segundos, Time levou) {
        var gol = new Gol(jogador, this.getInicioPartida().plusMinutes(minutos).plusSeconds(segundos), levou);

        this.gols.add(gol);
    }

    public int getQuantidadeGols(Time time) {
        int golsTime = 0;
        
        for (var gol : this.gols) {
            if (!gol.isAnulado() && gol.getFez().equals(time))
                golsTime++; 
        }

        return golsTime;
    }   

    public int getQuantidadeGols() {
        return (int) gols.stream().filter(g -> !g.isAnulado()).count();
    }   

    public int getGolsAnulados() {
        return (int) gols.stream().filter(g -> (g.isAnulado())).count();
    }   
}