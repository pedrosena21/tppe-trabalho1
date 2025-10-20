package tppe.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.campeonato.Campeonato;
import com.campeonato.Time;
import com.campeonato.Rodada;
import com.campeonato.Partida;

import java.util.List;
import java.util.ArrayList;

public class CampeonatoTest {

  private Campeonato campeonato;
  private Time timeA;
  private Time timeB;
  private Time timeC;

  @BeforeEach
  void setUp() {
    campeonato = new Campeonato();
    timeA = new Time("Flamengo", "Maracanã");
    timeB = new Time("Vasco", "São Januário");
    timeC = new Time("Fluminense", "Maracanã");
  }

  @Test
  void testCampeonatoInicializadoComListasVazias() {
    List<Time> times = campeonato.getTimes();
    List<Rodada> rodadas = campeonato.getRodadas();

    assertNotNull(times, "Lista de times não deve ser nula");
    assertTrue(times.isEmpty(), "Lista de times deve estar vazia inicialmente");

    assertNotNull(rodadas, "Lista de rodadas não deve ser nula");
    assertTrue(rodadas.isEmpty(), "Lista de rodadas deve estar vazia inicialmente");
  }

  @Test
  void testAdicionarTimeAumentaListaDeTimes() {
    campeonato.adicionarTime(timeA);
    campeonato.adicionarTime(timeB);

    List<Time> times = campeonato.getTimes();
    assertEquals(2, times.size(), "Deve conter 2 times após adicionar dois times");
    assertTrue(times.contains(timeA), "Lista deve conter Time A");
    assertTrue(times.contains(timeB), "Lista deve conter Time B");
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
  void testCalcularPontuacaoTimeSemPartidasRetornaZero() {
    campeonato.adicionarTime(timeA);

    short pontuacao = campeonato.calcularPontuacaoTime(timeA);

    assertEquals(0, pontuacao, "Pontuação deve ser zero sem partidas");
  }

  @Test
  void testCalcularPontuacaoTimeComVitoriaRetornaTresPontos() {
    campeonato.adicionarTime(timeA);
    campeonato.adicionarTime(timeB);

    Rodada rodada = new Rodada(1);
    Partida partida = new Partida(timeA, timeB, 2, 1, 0, 0, 0, 0);
    rodada.inserirPartida(partida);
    campeonato.adicionarRodada(rodada);

    short pontuacaoTimeA = campeonato.calcularPontuacaoTime(timeA);
    short pontuacaoTimeB = campeonato.calcularPontuacaoTime(timeB);

    assertEquals(3, pontuacaoTimeA, "Time A deve ter 3 pontos pela vitória");
    assertEquals(0, pontuacaoTimeB, "Time B deve ter 0 pontos pela derrota");
  }

  @Test
  void testCalcularPontuacaoTimeComEmpateRetornaUmPonto() {
    campeonato.adicionarTime(timeA);
    campeonato.adicionarTime(timeB);

    Rodada rodada = new Rodada(1);
    Partida partida = new Partida(timeA, timeB, 1, 1, 0, 0, 0, 0);
    rodada.inserirPartida(partida);
    campeonato.adicionarRodada(rodada);

    short pontuacaoTimeA = campeonato.calcularPontuacaoTime(timeA);
    short pontuacaoTimeB = campeonato.calcularPontuacaoTime(timeB);

    assertEquals(1, pontuacaoTimeA, "Time A deve ter 1 ponto pelo empate");
    assertEquals(1, pontuacaoTimeB, "Time B deve ter 1 ponto pelo empate");
  }

  @Test
  void testCalcularPontuacaoTimeMultiplasPartidasSomaPontosCorretamente() {
    campeonato.adicionarTime(timeA);
    campeonato.adicionarTime(timeB);
    campeonato.adicionarTime(timeC);

    Rodada rodada1 = new Rodada(1);
    Rodada rodada2 = new Rodada(2);

    Partida partida1 = new Partida(timeA, timeB, 2, 1, 0, 0, 0, 0);
    Partida partida2 = new Partida(timeA, timeC, 1, 1, 0, 0, 0, 0);

    rodada1.inserirPartida(partida1);
    rodada2.inserirPartida(partida2);

    campeonato.adicionarRodada(rodada1);
    campeonato.adicionarRodada(rodada2);

    short pontuacaoTimeA = campeonato.calcularPontuacaoTime(timeA);

    assertEquals(4, pontuacaoTimeA, "Time A deve ter 4 pontos (3 da vitória + 1 do empate)");
  }

  @Test
  void testCalcularClassificacaoComTimesSemPontosOrdenaPorNome() {
    Time timeZ = new Time("Zeta", "Estádio Z");
    Time timeA = new Time("Alfa", "Estádio A");
    Time timeM = new Time("Mega", "Estádio M");

    campeonato.adicionarTime(timeZ);
    campeonato.adicionarTime(timeA);
    campeonato.adicionarTime(timeM);

    campeonato.calcularClassificacao();
    List<Time> times = campeonato.getTimes();

    assertEquals(timeA, times.get(0), "Primeiro deve ser Alfa (ordem alfabética)");
    assertEquals(timeM, times.get(1), "Segundo deve ser Mega (ordem alfabética)");
    assertEquals(timeZ, times.get(2), "Terceiro deve ser Zeta (ordem alfabética)");
  }

  @Test
  void testCalcularClassificacaoOrdenaPorPontosDecrescente() {
    campeonato.adicionarTime(timeA);
    campeonato.adicionarTime(timeB);
    campeonato.adicionarTime(timeC);

    Rodada rodada = new Rodada(1);
    Partida partida1 = new Partida(timeB, timeC, 2, 0, 0, 0, 0, 0);
    Partida partida2 = new Partida(timeC, timeA, 1, 1, 0, 0, 0, 0);

    rodada.inserirPartida(partida1);
    rodada.inserirPartida(partida2);
    campeonato.adicionarRodada(rodada);

    campeonato.calcularClassificacao();
    List<Time> times = campeonato.getTimes();

    assertEquals(timeB, times.get(0), "Time B deve estar em primeiro com 3 pontos");
    assertEquals(timeC, times.get(1), "Time C deve estar em segundo com 1 ponto");
    assertEquals(timeA, times.get(2), "Time A deve estar em terceiro com 1 ponto");
  }

  @Test
  void testCalcularClassificacaoComEmpateEmPontosOrdenaPorSaldoGols() {
    Time timeD = new Time("Time D", "Estádio D");
    Time timeE = new Time("Time E", "Estádio E");

    campeonato.adicionarTime(timeD);
    campeonato.adicionarTime(timeE);

    Rodada rodada = new Rodada(1);
    Partida partida = new Partida(timeD, timeE, 4, 1, 0, 0, 0, 0);

    rodada.inserirPartida(partida);
    campeonato.adicionarRodada(rodada);

    campeonato.calcularClassificacao();
    List<Time> times = campeonato.getTimes();

    assertEquals(timeD, times.get(0), "Time D deve estar em primeiro com melhor saldo");
    assertEquals(timeE, times.get(1), "Time E deve estar em segundo com pior saldo");
  }

  @Test
  void testConstrutorComParametrosInicializaListasCorretamente() {
    List<Time> times = new ArrayList<>();
    times.add(timeA);
    times.add(timeB);

    List<Rodada> rodadas = new ArrayList<>();
    Rodada rodada = new Rodada(1);
    rodadas.add(rodada);

    Campeonato campeonatoComParametros = new Campeonato(times, rodadas);

    assertEquals(2, campeonatoComParametros.getTimes().size(), "Deve conter 2 times");
    assertEquals(1, campeonatoComParametros.getRodadas().size(), "Deve conter 1 rodada");
    assertTrue(campeonatoComParametros.getTimes().contains(timeA), "Deve conter Time A");
    assertTrue(campeonatoComParametros.getTimes().contains(timeB), "Deve conter Time B");
  }

  @Test
  void testToStringRetornaInformacoesDoCampeonato() {
    campeonato.adicionarTime(timeA);
    campeonato.adicionarTime(timeB);
    campeonato.adicionarRodada(new Rodada(1));

    String resultado = campeonato.toString();

    assertNotNull(resultado, "toString não deve retornar nulo");
    assertTrue(resultado.contains("2 times"), "Deve conter informação sobre quantidade de times");
    assertTrue(resultado.contains("1 rodadas"), "Deve conter informação sobre quantidade de rodadas");
  }

  @Test
  void testCalcularPontuacaoTimeNaoEncontradoRetornaZero() {
    Time timeForaDoCampeonato = new Time("Time X", "Estádio X");
    campeonato.adicionarTime(timeA);

    short pontuacao = campeonato.calcularPontuacaoTime(timeForaDoCampeonato);

    assertEquals(0, pontuacao, "Time fora do campeonato deve ter pontuação zero");
  }

  @Test
  void testCalcularClassificacaoComRodadaVaziaNaoLancaExcecao() {
    campeonato.adicionarTime(timeA);
    campeonato.adicionarTime(timeB);

    Rodada rodadaVazia = new Rodada(1);
    campeonato.adicionarRodada(rodadaVazia);

    assertDoesNotThrow(() -> campeonato.calcularClassificacao(),
        "Não deve lançar exceção com rodada vazia");
  }
}