package tppe.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.campeonato.Campeonato;
import com.campeonato.Confronto;
import com.campeonato.Jogador;
import com.campeonato.Time;
import com.campeonato.exception.CampeonatoLotadoException;
import com.campeonato.exception.JogadorNaoEncontradorException;
import com.campeonato.utils.Limites;
import com.campeonato.Rodada;
import com.campeonato.Partida;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class CampeonatoTest {

	private Campeonato campeonato;

	@BeforeEach
    void setupVinteTimes() throws CampeonatoLotadoException {

		campeonato = new Campeonato();

        // Lista dos 20 times da Série A (Referência 2024/2025)
        List<Time> timesBrasileirao = Arrays.asList(
            new Time("Flamengo", "Maracanã"),
            new Time("Palmeiras", "Allianz Parque"),
            new Time("Atlético-MG", "Arena MRV"),
            new Time("Grêmio", "Arena do Grêmio"),
            new Time("São Paulo", "Morumbi"),
            new Time("Internacional", "Beira-Rio"),
            new Time("Vasco", "São Januário"),
            new Time("Fluminense", "Maracanã"),
            new Time("Botafogo", "Nilton Santos"),
            new Time("Corinthians", "Neo Química Arena"),
            new Time("Cruzeiro", "Mineirão"),
            new Time("Athletico-PR", "Arena da Baixada"),
            new Time("Bahia", "Fonte Nova"),
            new Time("Vitória", "Barradão"),
            new Time("Cuiabá", "Arena Pantanal"),
            new Time("Fortaleza", "Castelão"),
            new Time("Juventude", "Alfredo Jaconi"),
            new Time("Bragantino", "Nabi Abi Chedid"),
            new Time("Atlético-GO", "Antônio Accioly"),
            new Time("Santos", "Vila Belmiro")
        );
        
        // Garante que temos 20 times para o teste
        assertEquals(Limites.MAXIMO_TIMES_CAMPEONATO.get(), timesBrasileirao.size(), "A lista de times do Brasileirão deve ter 20 clubes.");

        // Adiciona todos os times ao campeonato
        for (Time time : timesBrasileirao) {
            campeonato.adicionarTime(time);
        }
	}

	@Test
	void testTimesGeradosCorretamente() {
        // Verifica o estado após o setup (Sanity check)
        assertEquals(Limites.MAXIMO_TIMES_CAMPEONATO.get(), campeonato.getTimes().size(), "O campeonato deve ter exatamente 20 times.");
        
        // 20 times geram 20 * 19 / 2 = 190 confrontos
        int confrontosEsperados = 20 * 19 / 2; 
        assertEquals(confrontosEsperados, campeonato.getConfrontos().size(), 
                     "A lista de confrontos deve ter sido criada com 190 itens.");
	}

	@Test
	void testAdicionarRodadaAumentaListaDeRodadas() {
		Rodada rodada1 = new Rodada(1);
		Rodada rodada2 = new Rodada(2);

		campeonato.adicionarRodada(rodada1);
		campeonato.adicionarRodada(rodada2);

		List<Rodada> rodadas = campeonato.getRodadas();
		assertEquals(2, rodadas.size(), "Deve conter 2 rodadas após adicionar duas rodadas");
		assertEquals(1, rodadas.get(0).getNumeroRodada(), "Primeira rodada deve ter número 1");
		assertEquals(2, rodadas.get(1).getNumeroRodada(), "Segunda rodada deve ter número 2");
	}

	@Test
	void testCalcularPontuacaoTimeSemPartidasRetornaZero() throws CampeonatoLotadoException {
		var timeA = campeonato.getTimes().get(0);

		short pontuacao = campeonato.calcularPontuacaoTime(timeA);

		assertEquals(0, pontuacao, "Pontuação deve ser zero sem partidas");
	}

	@Test
	void testCalcularPontuacaoTimeComVitoriaRetornaTresPontos() throws CampeonatoLotadoException, JogadorNaoEncontradorException {
		var timeA = campeonato.getTimes().get(0);
		var timeB = campeonato.getTimes().get(1);

		Rodada rodada = new Rodada(1);
		Partida partida = new Partida(timeA, timeB, LocalDateTime.now());
		rodada.inserirPartida(partida);
		campeonato.adicionarRodada(rodada);
		
		var jogador1 = new Jogador(timeA, "Carlos", 11);
		partida.marcarGol(jogador1, 0, 0, timeB);

		short pontuacaoTimeA = campeonato.calcularPontuacaoTime(timeA);
		short pontuacaoTimeB = campeonato.calcularPontuacaoTime(timeB);

		assertEquals(3, pontuacaoTimeA, "Time A deve ter 3 pontos pela vitória");
		assertEquals(0, pontuacaoTimeB, "Time B deve ter 0 pontos pela derrota");
	}

	@Test
	void testCalcularPontuacaoTimeComEmpateRetornaUmPonto() throws CampeonatoLotadoException {
		var timeA = campeonato.getTimes().get(0);
		var timeB = campeonato.getTimes().get(1);

		Rodada rodada = new Rodada(1);
		Partida partida = new Partida(timeA, timeB, LocalDateTime.now());
		rodada.inserirPartida(partida);
		campeonato.adicionarRodada(rodada);

		short pontuacaoTimeA = campeonato.calcularPontuacaoTime(timeA);
		short pontuacaoTimeB = campeonato.calcularPontuacaoTime(timeB);

		assertEquals(1, pontuacaoTimeA, "Time A deve ter 1 ponto pelo empate");
		assertEquals(1, pontuacaoTimeB, "Time B deve ter 1 ponto pelo empate");
	}

	@Test
	void testCalcularPontuacaoTimeMultiplasPartidasSomaPontosCorretamente() throws CampeonatoLotadoException, JogadorNaoEncontradorException {
		var timeA = campeonato.getTimes().get(0);
		var timeB = campeonato.getTimes().get(1);
		var timeC = campeonato.getTimes().get(2);

		Rodada rodada1 = new Rodada(1);
		Rodada rodada2 = new Rodada(2);

		Partida partida1 = new Partida(timeA, timeB, LocalDateTime.now());
		Partida partida2 = new Partida(timeA, timeC, LocalDateTime.now());

		// Time A vence partida1
		var jogador1 = new Jogador(timeA, "Carlos", 11);
		partida1.marcarGol(jogador1, 0, 0, timeB);

		rodada1.inserirPartida(partida1);
		rodada2.inserirPartida(partida2);
		
		partida1.finalizarPartida();
		partida2.finalizarPartida();

		campeonato.adicionarRodada(rodada1);
		campeonato.adicionarRodada(rodada2);

		int pontuacaoTimeA = timeA.getPontos();

		assertEquals(4, pontuacaoTimeA, "Time A deve ter 4 pontos (3 da vitória + 1 do empate)");
	}

	@Test
	void testCalcularClassificacaoComTimesSemPontosOrdenaPorNome() throws CampeonatoLotadoException {
		campeonato.calcularClassificacao();

		List<Time> times = campeonato.getTimes();

		var athleticoParanaense = campeonato.getTimes().stream()
			.filter(t -> t.getNome().equals("Athletico-PR"))
			.findFirst().orElse(null);
		var atleticoGoianiense = campeonato.getTimes().stream()
			.filter(t -> t.getNome().equals("Atlético-GO"))
			.findFirst().orElse(null);
		var atleticoMineiro = campeonato.getTimes().stream()
			.filter(t -> t.getNome().equals("Atlético-MG"))
			.findFirst().orElse(null);

		assertEquals(athleticoParanaense, times.get(0), "Primeiro deve ser Athletico-PR (ordem alfabética)");
		assertEquals(atleticoGoianiense, times.get(1), "Segundo deve ser Atlético-GO (ordem alfabética)");
		assertEquals(atleticoMineiro, times.get(2), "Terceiro deve ser Atlético-MG (ordem alfabética)");
	}

	@Test
	void testCalcularClassificacaoOrdenaPorPontosDecrescente() throws CampeonatoLotadoException, JogadorNaoEncontradorException {
		var timeA = campeonato.getTimes().get(0);
		var timeB = campeonato.getTimes().get(1);
		var timeC = campeonato.getTimes().get(2);
		
		Rodada rodada = new Rodada(1);
		Partida partida1 = new Partida(timeA, timeB, LocalDateTime.of(2025, 11, 6, 16, 15));
		Partida partida2 = new Partida(timeA, timeC, LocalDateTime.of(2025, 11, 7, 16, 15));

		// Time B vence Time A
		var jogador1 = new Jogador(timeB, "Carlos", 11);
		partida1.marcarGol(jogador1, 0, 0, timeA);

		rodada.inserirPartida(partida1);
		rodada.inserirPartida(partida2);
		partida1.finalizarPartida();
		partida2.finalizarPartida();

		campeonato.adicionarRodada(rodada);

		campeonato.calcularClassificacao();
		List<Time> times = campeonato.getTimes();

		assertEquals(timeB, times.get(0), "Time B deve estar em primeiro com 3 pontos");
		assertEquals(timeC, times.get(1), "Time C deve estar em segundo com 1 ponto");
		assertEquals(timeA, times.get(2), "Time A deve estar em terceiro com 1 ponto");
	}

	@Test
	void testCalcularClassificacaoComEmpateEmPontosOrdenaPorSaldoGols() throws CampeonatoLotadoException, JogadorNaoEncontradorException {
		var flamengo = campeonato.getTimes().get(0);
		var gremio = campeonato.getTimes().get(3);

		Partida partida = new Partida(flamengo, gremio, LocalDateTime.now());
		
		var jogador1 = new Jogador(flamengo, "Carlos", 11);
		partida.marcarGol(jogador1, 15, 0, gremio);
		
		Rodada rodada = new Rodada(1);
		rodada.inserirPartida(partida);
		partida.finalizarPartida();

		campeonato.adicionarRodada(rodada);

		campeonato.calcularClassificacao();

		List<Time> times = campeonato.getTimes();

		assertEquals(flamengo, times.get(0), "Flamengo deve estar em primeiro com melhor saldo");
		assertEquals(3, flamengo.getPontos());
	}

	@Test
	void testConstrutorComParametrosInicializaListasCorretamente() {
		List<Time> times = new ArrayList<>();
		times.add(new Time("Time A", "Estádio A"));
		times.add(new Time("Time B", "Estádio B"));

		List<Rodada> rodadas = new ArrayList<>();
		Rodada rodada = new Rodada(1);
		rodadas.add(rodada);

		Campeonato campeonatoComParametros = new Campeonato(times, rodadas);

		assertEquals(2, campeonatoComParametros.getTimes().size(), "Deve conter 2 times");
		assertEquals(1, campeonatoComParametros.getRodadas().size(), "Deve conter 1 rodada");
		assertEquals("Time A", campeonatoComParametros.getTimes().get(0).getNome(), "Deve conter Time A");
		assertEquals("Time B", campeonatoComParametros.getTimes().get(1).getNome(), "Deve conter Time B");
	}

	@Test
    void testToStringRetornaInformacoesDoCampeonato() throws CampeonatoLotadoException {
        // Cria um novo campeonato para testar o cenário de 2 times e 1 rodada
        Campeonato c = new Campeonato();
        Time timeA = new Time("Time A", "Estádio A");
        Time timeB = new Time("Time B", "Estádio B");
        
        c.adicionarTime(timeA);
        c.adicionarTime(timeB);
        c.adicionarRodada(new Rodada(1));

        String resultado = c.toString();

        assertNotNull(resultado, "toString não deve retornar nulo");
        assertTrue(resultado.contains("2 times"), "Deve conter informação sobre quantidade de times");
        assertTrue(resultado.contains("1 rodadas"), "Deve conter informação sobre quantidade de rodadas");
    }

	@Test
    void testCalcularPontuacaoTimeNaoEncontradoRetornaZero() throws CampeonatoLotadoException {
        Time timeForaDoCampeonato = new Time("Time X", "Estádio X");
        
        short pontuacao = campeonato.calcularPontuacaoTime(timeForaDoCampeonato);

        assertEquals(0, pontuacao, "Time fora do campeonato deve ter pontuação zero");
    }

	@Test
    void testCalcularClassificacaoComRodadaVaziaNaoLancaExcecao() throws CampeonatoLotadoException {
        Rodada rodadaVazia = new Rodada(99);
        campeonato.adicionarRodada(rodadaVazia);

        assertDoesNotThrow(() -> campeonato.calcularClassificacao(),
                "Não deve lançar exceção com rodada vazia");
    }

	@Test
    void gerarRodada_primeiraRodada_deveEstarCorreta() {
        int numeroRodada = 1;
        Rodada rodada = campeonato.gerarRodada(numeroRodada);

        assertEquals(numeroRodada, rodada.getNumeroRodada(), "O número da rodada deve ser o esperado.");

        int partidasEsperadas = Limites.MAXIMO_CONFRONTOS_POR_RODADA.get() * 2;
        assertEquals(partidasEsperadas, rodada.getPartidas().size(),
                "A rodada deve ter o número correto de partidas (2 confrontos * 2 jogos).");
				
        List<Partida> partidas = rodada.getPartidas();
        
        Confronto primeiroConfronto = campeonato.getConfrontos().get(0);

        Partida partidaIda = partidas.get(0);
        assertEquals(primeiroConfronto.getTime1(), partidaIda.getTimeCasa(), "O Time Casa deve ser o Time1 do confronto.");
		assertEquals(primeiroConfronto.getTime2(), partidaIda.getTimeVisitante(), "O Time Visitante deve ser o Time2 do confronto.");

        Partida partidaVolta = partidas.get(1);

        assertEquals(primeiroConfronto.getTime1(), partidaIda.getTimeCasa(), "No código, o primeiro jogo é T1 vs T2.");
        assertEquals(primeiroConfronto.getTime2(), partidaIda.getTimeVisitante(), "No código, o primeiro jogo é T1 vs T2.");

        assertEquals(primeiroConfronto.getTime2(), partidaVolta.getTimeCasa(), "No código, o segundo jogo é T2 vs T1.");
        assertEquals(primeiroConfronto.getTime1(), partidaVolta.getTimeVisitante(), "No código, o segundo jogo é T2 vs T1.");
    }

	@Test
	void testAtualizarEstatisticasTimeVitoria() throws CampeonatoLotadoException, JogadorNaoEncontradorException {
		var timeA = campeonato.getTimes().get(0);
		var timeB = campeonato.getTimes().get(1);

		Rodada rodada = new Rodada(1);
		Partida partida = new Partida(timeA, timeB, LocalDateTime.now());
		
		// TimeA faz 2 gols, TimeB não faz gol
		var jogador1 = new Jogador(timeA, "Carlos", 11);
		var jogador2 = new Jogador(timeA, "Pedro", 9);
		partida.marcarGol(jogador1, 10, 0, timeB);
		partida.marcarGol(jogador2, 25, 0, timeB);

		rodada.inserirPartida(partida);
		campeonato.adicionarRodada(rodada);
		partida.finalizarPartida();


		assertEquals(3, timeA.getPontos(), "Time A deve ter 3 pontos");
		assertEquals(1, timeA.getVitorias(), "Time A deve ter 1 vitória");
		assertEquals(2, timeA.getGolsMarcados(), "Time A deve ter 2 gols marcados");
		assertEquals(0, timeA.getGolsSofridos(), "Time A não deve ter gols sofridos");
		assertEquals(2, timeA.getSaldoGols(), "Time A deve ter saldo de 2 gols");

		assertEquals(0, timeB.getPontos(), "Time B não deve ter pontos");
		assertEquals(1, timeB.getDerrotas(), "Time B deve ter 1 derrota");
		assertEquals(0, timeB.getGolsMarcados(), "Time B não deve ter gols marcados");
		assertEquals(2, timeB.getGolsSofridos(), "Time B deve ter 2 gols sofridos");
		assertEquals(-2, timeB.getSaldoGols(), "Time B deve ter saldo de -2 gols");
	}

	@Test
	void testAtualizarEstatisticasTimeEmpate() throws CampeonatoLotadoException, JogadorNaoEncontradorException {
		var timeA = campeonato.getTimes().get(0);
		var timeB = campeonato.getTimes().get(1);

		Rodada rodada = new Rodada(1);
		Partida partida = new Partida(timeA, timeB, LocalDateTime.now());
		
		// Empate 1x1
		var jogador1 = new Jogador(timeA, "Carlos", 11);
		var jogador2 = new Jogador(timeB, "Roberto", 10);
		partida.marcarGol(jogador1, 10, 0, timeB);
		partida.marcarGol(jogador2, 30, 0, timeA);
		partida.finalizarPartida();

		rodada.inserirPartida(partida);
		campeonato.adicionarRodada(rodada);

		assertEquals(1, timeA.getPontos(), "Time A deve ter 1 ponto");
		assertEquals(1, timeA.getEmpates(), "Time A deve ter 1 empate");
		assertEquals(1, timeA.getGolsMarcados(), "Time A deve ter 1 gol marcado");
		assertEquals(1, timeA.getGolsSofridos(), "Time A deve ter 1 gol sofrido");
		assertEquals(0, timeA.getSaldoGols(), "Time A deve ter saldo 0");

		assertEquals(1, timeB.getPontos(), "Time B deve ter 1 ponto");
		assertEquals(1, timeB.getEmpates(), "Time B deve ter 1 empate");
		assertEquals(1, timeB.getGolsMarcados(), "Time B deve ter 1 gol marcado");
		assertEquals(1, timeB.getGolsSofridos(), "Time B deve ter 1 gol sofrido");
		assertEquals(0, timeB.getSaldoGols(), "Time B deve ter saldo 0");
	}
}