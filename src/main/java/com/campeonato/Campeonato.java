package com.campeonato;

import java.util.List;

import com.campeonato.exception.CampeonatoLotadoException;
import com.campeonato.utils.Limites;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Campeonato {
	private List<Time> times;
	private List<Rodada> rodadas;
	private List<Confronto> confrontos;

	private LocalDateTime inicioCampeonato = LocalDateTime.now();

	public Campeonato() {
		this.times = new ArrayList<>();
		this.rodadas = new ArrayList<>();
		this.confrontos = new ArrayList<>();
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

	private void criarConfrontos() {
		for (int i = 0; i < this.times.size(); i++) {
			for (int j = i + 1; j < this.times.size(); j++) {
				this.confrontos.add(new Confronto(this.times.get(i), this.times.get(j)));
			}
		}

		Collections.shuffle(this.confrontos);
	}

	public void adicionarTime(Time time) throws CampeonatoLotadoException {
		if (this.times.size() >= Limites.MAXIMO_TIMES_CAMPEONATO.get()) {
			throw new CampeonatoLotadoException();
		}

		this.times.add(time);

		if (this.times.size() == Limites.MAXIMO_TIMES_CAMPEONATO.get()) {
			criarConfrontos();
		}

	}

	public void adicionarRodada(Rodada rodada) {
		this.rodadas.add(rodada);
	}

	public Rodada gerarRodada(int numeroRodada) {
		if (numeroRodada > 38) 
			return null;
		
		var novaRodada = new Rodada(numeroRodada);
		int offSet = (numeroRodada - 1) * Limites.MAXIMO_CONFRONTOS_POR_RODADA.get();
		
		for (Confronto c : this.confrontos.subList(offSet, offSet + Limites.MAXIMO_CONFRONTOS_POR_RODADA.get())){
			novaRodada.inserirPartida(new Partida(c.getTime1(), c.getTime2(), this.inicioCampeonato.plusDays(numeroRodada)));
			novaRodada.inserirPartida(new Partida(c.getTime2(), c.getTime1(), this.inicioCampeonato.plusDays(numeroRodada)));
		}

		return novaRodada;
	}

	public short calcularPontuacaoTime(Time time) {
		return new CalculadoraPontuacaoTime(this, time).calcular();
	}

	public void calcularClassificacao() {
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

	@Override
	public String toString() {
		return String.format("Campeonato com %d times e %d rodadas", times.size(), rodadas.size());
	}

	public List<Confronto> getConfrontos() {
		return this.confrontos;
	}
}