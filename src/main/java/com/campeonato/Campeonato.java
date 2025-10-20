package com.campeonato;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Campeonato {
  private List<Time> times;
  private List<Rodada> rodadas;

  public Campeonato() {
    this.times = new ArrayList<>();
    this.rodadas = new ArrayList<>();
  }

  public Campeonato(List<Time> times, List<Rodada> rodadas) {
    this.times = times;
    this.rodadas = rodadas;
  }

  public List<Time> getTimes() {
    return Collections.unmodifiableList(times);
  }

  public List<Rodada> getRodadas() {
    return Collections.unmodifiableList(rodadas);
  }

  public void adicionarTime(Time time) {
    this.times.add(time);
  }

  public void adicionarRodada(Rodada rodada) {
    this.rodadas.add(rodada);
  }

  public short calcularPontuacaoTime(Time time) {
    short pontuacao = 0;

    for (Rodada rodada : rodadas) {
      for (Partida partida : rodada.getPartidas()) {
        if (partida.getTimeCasa().equals(time) || partida.getTimeVisitante().equals(time)) {
          int golsTime = partida.getTimeCasa().equals(time) ? partida.getGolsTimeCasa()
              : partida.getGolsTimeVisitante();
          int golsAdversario = partida.getTimeCasa().equals(time) ? partida.getGolsTimeVisitante()
              : partida.getGolsTimeCasa();

          if (golsTime > golsAdversario) {
            pontuacao += 3;
          } else if (golsTime == golsAdversario) {
            pontuacao += 1;
          }
        }
      }
    }

    return pontuacao;
  }

  public void calcularClassificacao() {
    for (Time time : times) {
      atualizarEstatisticasTime(time);
    }
    Collections.sort(times, new Comparator<Time>() {
      @Override
      public int compare(Time t1, Time t2) {
        int comparacaoPontos = Integer.compare(t2.getPontos(), t1.getPontos());
        if (comparacaoPontos != 0)
          return comparacaoPontos;

        int comparacaoSaldo = Integer.compare(t2.getSaldoGols(), t1.getSaldoGols());
        if (comparacaoSaldo != 0)
          return comparacaoSaldo;

        int comparacaoGolsMarcados = Integer.compare(t2.getGolsMarcados(), t1.getGolsMarcados());
        if (comparacaoGolsMarcados != 0)
          return comparacaoGolsMarcados;

        return t1.getNome().compareTo(t2.getNome());
      }
    });

    System.out.println("=== CLASSIFICAÇÃO DO CAMPEONATO ===");
    System.out.println("Pos. Time                  | Pts | V  | E  | D  | GM  | GS  | SG  | CA | CV");
    System.out.println("----------------------------------------------------------------------------");

    for (int i = 0; i < times.size(); i++) {
      Time time = times.get(i);
      System.out.printf("%2d. %s%n", i + 1, time.toString());
    }
  }

  private void atualizarEstatisticasTime(Time time) {
    int vitorias = 0;
    int empates = 0;
    int derrotas = 0;
    int golsMarcados = 0;
    int golsSofridos = 0;
    int cartoesAmarelos = 0;
    int cartoesVermelhos = 0;

    for (Rodada rodada : rodadas) {
      for (Partida partida : rodada.getPartidas()) {
        if (partida.getTimeCasa().equals(time)) {
          golsMarcados += partida.getGolsTimeCasa();
          golsSofridos += partida.getGolsTimeVisitante();
          cartoesAmarelos += partida.getCartoesAmarelosTimeCasa();
          cartoesVermelhos += partida.getCartoesVermelhosTimeCasa();

          if (partida.getGolsTimeCasa() > partida.getGolsTimeVisitante()) {
            vitorias++;
          } else if (partida.getGolsTimeCasa() == partida.getGolsTimeVisitante()) {
            empates++;
          } else {
            derrotas++;
          }
        } else if (partida.getTimeVisitante().equals(time)) {
          golsMarcados += partida.getGolsTimeVisitante();
          golsSofridos += partida.getGolsTimeCasa();
          cartoesAmarelos += partida.getCartoesAmarelosTimeVisitante();
          cartoesVermelhos += partida.getCartoesVermelhosTimeVisitante();

          if (partida.getGolsTimeVisitante() > partida.getGolsTimeCasa()) {
            vitorias++;
          } else if (partida.getGolsTimeVisitante() == partida.getGolsTimeCasa()) {
            empates++;
          } else {
            derrotas++;
          }
        }
      }
    }
  }

  @Override
  public String toString() {
    return String.format("Campeonato com %d times e %d rodadas", times.size(), rodadas.size());
  }
}