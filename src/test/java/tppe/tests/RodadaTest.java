package tppe.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import com.campeonato.Partida;
import com.campeonato.Rodada;
import com.campeonato.Time;
import com.campeonato.exception.PartidaJaExisteNaRodadaException;
import com.campeonato.exception.RodadaLotadaException;

class RodadaTest {
	private Rodada rodadaTeste;
	private Time time1;
	private Time time2;
	
	@BeforeEach
	void setUp() {
		rodadaTeste = new Rodada (1);
		
		time1 = new Time("time1", "estadio1");
		time2 = new Time("time2", "estadio2");
	}
	
	@Test
	void testInserirPartida() throws RodadaLotadaException, PartidaJaExisteNaRodadaException {
		Partida jogo1 = new Partida(time1, time2, LocalDateTime.now());
		
		rodadaTeste.inserirPartida(jogo1);
		
		assertEquals(1, rodadaTeste.getPartidas().size());
		
		Partida jogo2 = new Partida(time2, time1, LocalDateTime.now().plusDays(1));
		
		rodadaTeste.inserirPartida(jogo2);
		
		assertEquals(2, rodadaTeste.getPartidas().size());
	}
	
	@Test
	void testNumeroRodada() {
		assertEquals(1, rodadaTeste.getNumeroRodada());
		
		Rodada rodada38 = new Rodada(38);
		assertEquals(38, rodada38.getNumeroRodada());
	}
	
	@Test
	void testToString() throws RodadaLotadaException, PartidaJaExisteNaRodadaException {
		assertEquals("Rodada 1(0 jogos)", rodadaTeste.toString());
		
		rodadaTeste.inserirPartida(new Partida(time1, time2, LocalDateTime.now()));
		assertEquals("Rodada 1(1 jogos)", rodadaTeste.toString());
	}

	@Test
	void construcaoRodada() throws RodadaLotadaException, PartidaJaExisteNaRodadaException {
		assertEquals("Rodada 1(0 jogos)", rodadaTeste.toString());
		
		rodadaTeste.inserirPartida(new Partida(time1, time2, LocalDateTime.now()));
		assertEquals("Rodada 1(1 jogos)", rodadaTeste.toString());
	}
}
