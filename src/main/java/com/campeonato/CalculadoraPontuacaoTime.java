package com.campeonato;

public class CalculadoraPontuacaoTime {

    private Campeonato campeonato;
    private Time time;
    
    public CalculadoraPontuacaoTime(Campeonato campeonato, Time time) {
        this.campeonato = campeonato;
        this.time = time;    
    }

    public short calcular() {
        short pontuacaoTotal = 0;

		for (Rodada rodada : campeonato.getRodadas()) {
			for (Partida partida : rodada.getPartidas()) {

				if (partida.getTimeCasa().equals(time) || partida.getTimeVisitante().equals(time)) {
					Time adversario = partida.getTimeCasa().equals(time) ? partida.getTimeVisitante() : partida.getTimeCasa();
					
					int golsTime = partida.getQuantidadeGols(time);
					int golsAdversario = partida.getQuantidadeGols(adversario);
					
					pontuacaoTotal += calcularPontuacaoPartida(golsTime, golsAdversario);
				}
			}
		}

		return pontuacaoTotal;
    }
    
    private short calcularPontuacaoPartida(int golsTime, int golsAdversario) {
		if (golsTime > golsAdversario) {
			return 3;
		} else if (golsTime == golsAdversario) {
			return 1;
		}else {
			return 0;
		}
	}

}
