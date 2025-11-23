package com.campeonato;

public class CalculadoraPontuacaoTime {

    private Campeonato campeonato;
    private Time time;
    
    public CalculadoraPontuacaoTime(Campeonato campeonato, Time time) {
        this.campeonato = campeonato;
        this.time = time;    
    }

    public short calcular() {
        short pontuacao = 0;
		int golsTime = 0;
		int golsAdversario = 0;

		for (Rodada rodada : campeonato.getRodadas()) {
			for (Partida partida : rodada.getPartidas()) {

				if (partida.getTimeCasa().equals(time)) {
					golsTime = partida.getQuantidadeGols(partida.getTimeCasa());
					golsAdversario = partida.getQuantidadeGols(partida.getTimeVisitante());

					pontuacao += calcularPontuacaoPartida(pontuacao, golsTime, golsAdversario);

				}

				if (partida.getTimeVisitante().equals(time)) {
					golsTime = partida.getQuantidadeGols(partida.getTimeVisitante());
					golsAdversario = partida.getQuantidadeGols(partida.getTimeCasa());

					pontuacao += calcularPontuacaoPartida(pontuacao, golsTime, golsAdversario);
				}
			}
		}

		return pontuacao;
    }
    
    private short calcularPontuacaoPartida(short pontuacao, int golsTime, int golsAdversario) {
		if (golsTime > golsAdversario) {
			pontuacao += 3;
		} else if (golsTime == golsAdversario) {
			pontuacao += 1;
		}
		return pontuacao;
	}

}
