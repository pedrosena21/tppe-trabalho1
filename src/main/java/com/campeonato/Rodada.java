package com.campeonato;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class Rodada {
	private static final short PARTIDAS_POR_RODADA = 20;
	
	private final List<Partida> partidas;
	private final int numeroRodada;

	public Rodada(int numeroRodada) {
		this.partidas = new ArrayList<>(PARTIDAS_POR_RODADA);
		this.numeroRodada = numeroRodada;
	}
	
	public void inserirPartida(Partida partida) {
		this.partidas.add(partida);
	}

	public List<Partida> getPartidas(){
		return Collections.unmodifiableList(partidas);
	}
	
	public int getNumeroRodada() {
		return numeroRodada;
	}
	
	@Override
	public String toString() {
		return "Rodada "+ numeroRodada + "(" + partidas.size() + " jogos)";
	}
}
 