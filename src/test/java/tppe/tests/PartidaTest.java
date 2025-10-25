package tppe.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.campeonato.Gol;
import com.campeonato.Jogador;
import com.campeonato.Partida;
import com.campeonato.Time;
import com.campeonato.exception.JogadorNaoEncontradorException;

import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PartidaTest {

    private Partida partida;

    // Listas dos 11 titulares (Nome, Camisa)
    private static final List<Map.Entry<String, Integer>> JOGADORES_FLAMENGO = List.of(
            new SimpleEntry<>("Goleiro Fla", 1),
            new SimpleEntry<>("Lateral D", 2),
            new SimpleEntry<>("Zagueiro 1", 3),
            new SimpleEntry<>("Zagueiro 2", 4),
            new SimpleEntry<>("Lateral E", 6),
            new SimpleEntry<>("Volante A", 5),
            new SimpleEntry<>("Volante B", 8),
            new SimpleEntry<>("Meia Armador", 10),
            new SimpleEntry<>("Ponta Direita", 7),
            new SimpleEntry<>("Ponta Esquerda", 11),
            new SimpleEntry<>("Centroavante", 9));

    private static final List<Map.Entry<String, Integer>> JOGADORES_SAOPAULO = List.of(
            new SimpleEntry<>("Goleiro SP", 1),
            new SimpleEntry<>("Lateral D", 2),
            new SimpleEntry<>("Zagueiro 1", 3),
            new SimpleEntry<>("Zagueiro 2", 4),
            new SimpleEntry<>("Lateral E", 6),
            new SimpleEntry<>("Volante C", 5),
            new SimpleEntry<>("Volante D", 8),
            new SimpleEntry<>("Meia Armador", 10),
            new SimpleEntry<>("Ponta Direita", 7),
            new SimpleEntry<>("Ponta Esquerda", 11),
            new SimpleEntry<>("Centroavante", 9));

    @Nested
    class Cartoes {

        @BeforeEach
        void init() {
            var flamengo = new Time("Maracanã", "Flamengo");
            var saoPaulo = new Time("Vila Belmiro", "São Paulo");

            partida = new Partida(flamengo, saoPaulo, LocalDateTime.now());
        }

        @Test
        void pegarCartoesVermelhos() {
            var jogador = new Jogador(partida.getTimeCasa(), "Carlos", 12);
            partida.inserirCartaoVermelho(jogador);

            ArrayList<Jogador> expectedResponse = new ArrayList<>(Arrays.asList(jogador));

            assertEquals(expectedResponse, partida.getCartoesVermelhos(partida.getTimeCasa()));
            assertEquals(expectedResponse, partida.getCartoesVermelhos());
            assertEquals(new ArrayList<>(), partida.getCartoesAmarelos());
        }

        @Test
        void pegarCartoesAmarelos() {
            var jogador = new Jogador(partida.getTimeVisitante(), "Roberto", 11);
            partida.inserirCartaoAmarelo(jogador);

            ArrayList<Jogador> expectedResponse = new ArrayList<>(Arrays.asList(jogador));

            assertEquals(expectedResponse, partida.getCartoesAmarelos(partida.getTimeVisitante()));
            assertEquals(expectedResponse, partida.getCartoesAmarelos());
            assertEquals(new ArrayList<>(), partida.getCartoesVermelhos());
        }
    }

    @Nested
    class Gols {

        @BeforeEach
        void init() {
            var flamengo = new Time("Maracanã", "Flamengo");
            var saoPaulo = new Time("Vila Belmiro", "São Paulo");

            popularTimeComJogadores(flamengo, JOGADORES_FLAMENGO);
            popularTimeComJogadores(saoPaulo, JOGADORES_SAOPAULO);

            partida = new Partida(flamengo, saoPaulo, LocalDateTime.now());
        }

        private void popularTimeComJogadores(Time time, List<Map.Entry<String, Integer>> titulares) {
            if (titulares.size() != 11) {
                throw new IllegalArgumentException("A lista de titulares deve conter exatamente 11 jogadores.");
            }

            for (Map.Entry<String, Integer> par : titulares) {
                String nome = par.getKey();
                int camisa = par.getValue();

                time.adicionarJogador(new Jogador(time, nome, camisa));
            }
        }

        @Test
        @DisplayName("Deve retornar jogadores que fizeram gols em casa")
        void marcarGolCasa() throws JogadorNaoEncontradorException {
            var jogador = partida.getTimeCasa().getJogador(11);

            var gol = new Gol(jogador,
                    partida.getInicioPartida().plusMinutes(13).plusSeconds(12), partida.getTimeVisitante());

            partida.marcarGol(jogador, 13, 12, partida.getTimeVisitante());

            ArrayList<Gol> expectedResponse = new ArrayList<>(Arrays.asList(gol));

            assertEquals(expectedResponse, partida.getGols(partida.getTimeCasa()));
            assertEquals(expectedResponse, partida.getGols());
            assertEquals(new ArrayList<>(), partida.getGols(partida.getTimeVisitante()));
            assertFalse(partida.getGols().get(0).isGolContra());
        }

        @Test
        @DisplayName("Deve retornar jogadores que fizeram gols em fora de casa")
        void marcarGolFora() throws JogadorNaoEncontradorException {
            var jogador = partida.getTimeVisitante().getJogador(3);

            var gol = new Gol(jogador,
                    partida.getInicioPartida().plusMinutes(13).plusSeconds(12), partida.getTimeCasa());

            partida.marcarGol(jogador, 13, 12, partida.getTimeCasa());

            ArrayList<Gol> expectedGolsFora = new ArrayList<>(Arrays.asList(gol));
            ArrayList<Gol> expectedGolsCasa = new ArrayList<>(Arrays.asList());

            assertEquals(expectedGolsFora, partida.getGols(partida.getTimeVisitante()));
            assertEquals(expectedGolsCasa, partida.getGols(partida.getTimeCasa()));
            assertEquals(new ArrayList<>(), partida.getGols(partida.getTimeCasa()));
            assertFalse(partida.getGols().get(0).isGolContra());
        }

        @Test
        @DisplayName("Deve retornar jogadores que fizeram gols contra")
        void marcarGolContra() throws JogadorNaoEncontradorException {
            var jogador = partida.getTimeVisitante().getJogador(3);

            var gol = new Gol(jogador,
                    partida.getInicioPartida().plusMinutes(13).plusSeconds(12), partida.getTimeVisitante());

            partida.marcarGol(jogador, 13, 12, partida.getTimeVisitante());

            ArrayList<Gol> expectedResponse = new ArrayList<>(Arrays.asList(gol));

            assertEquals(expectedResponse, partida.getGols(partida.getTimeVisitante()));
            assertEquals(expectedResponse, partida.getGols(partida.getTimeVisitante()));
            assertTrue(partida.getGols().get(0).isGolContra());
        }

        @Test
        @DisplayName("Deve retornar 4 gols para casa e 1 para fora")
        void marcarGols() throws JogadorNaoEncontradorException {
            var jogador1 = partida.getTimeCasa().getJogador(3);
            var jogador2 = partida.getTimeVisitante().getJogador(1);
            var jogador3 = partida.getTimeVisitante().getJogador(2);

            var gol1 = new Gol(jogador1,
                    partida.getInicioPartida().plusMinutes(13).plusSeconds(12), partida.getTimeVisitante());
            var gol2 = new Gol(jogador2,
                    partida.getInicioPartida().plusMinutes(14).plusSeconds(12), partida.getTimeCasa());
            var gol3 = new Gol(jogador2,
                    partida.getInicioPartida().plusMinutes(54).plusSeconds(11), partida.getTimeCasa());
            var gol4 = new Gol(jogador3,
                    partida.getInicioPartida().plusMinutes(67).plusSeconds(12), partida.getTimeCasa());
            var gol5 = new Gol(jogador3,
                    partida.getInicioPartida().plusMinutes(87).plusSeconds(12), partida.getTimeCasa());

            partida.marcarGol(jogador1, 13, 12, partida.getTimeVisitante());
            partida.marcarGol(jogador2, 14, 12, partida.getTimeCasa());
            partida.marcarGol(jogador2, 54, 11, partida.getTimeCasa());
            partida.marcarGol(jogador3, 67, 12, partida.getTimeCasa());
            partida.marcarGol(jogador3, 87, 12, partida.getTimeCasa());

            ArrayList<Gol> expectedGolsTimeFora = new ArrayList<>(Arrays.asList(gol2, gol3, gol4, gol5));
            ArrayList<Gol> expectedGolsTimeCasa = new ArrayList<>(Arrays.asList(gol1));

            assertEquals(expectedGolsTimeCasa, partida.getGols(partida.getTimeCasa()));
            assertEquals(expectedGolsTimeFora, partida.getGols(partida.getTimeVisitante()));

            assertEquals(1, partida.getQuantidadeGols(partida.getTimeCasa()));
            assertEquals(4, partida.getQuantidadeGols(partida.getTimeVisitante()));
        }

        @Test
        @DisplayName("Deve retornar 4 gols para casa e 1 para fora")
        void anulandoGols() throws JogadorNaoEncontradorException {
            var jogador1 = partida.getTimeCasa().getJogador(3);
            var jogador2 = partida.getTimeVisitante().getJogador(1);
            var jogador3 = partida.getTimeVisitante().getJogador(2);

            var gol1 = new Gol(jogador1,
                    partida.getInicioPartida().plusMinutes(13).plusSeconds(12), partida.getTimeVisitante());
            var gol2 = new Gol(jogador2,
                    partida.getInicioPartida().plusMinutes(14).plusSeconds(12), partida.getTimeCasa());
            var gol3 = new Gol(jogador2,
                    partida.getInicioPartida().plusMinutes(54).plusSeconds(11), partida.getTimeCasa());
            var gol4 = new Gol(jogador3,
                    partida.getInicioPartida().plusMinutes(67).plusSeconds(12), partida.getTimeCasa());
            var gol5 = new Gol(jogador3,
                    partida.getInicioPartida().plusMinutes(87).plusSeconds(12), partida.getTimeCasa());

            partida.marcarGol(jogador1, 13, 12, partida.getTimeVisitante());
            partida.marcarGol(jogador2, 14, 12, partida.getTimeCasa());
            partida.marcarGol(jogador2, 54, 11, partida.getTimeCasa());
            partida.marcarGol(jogador3, 67, 12, partida.getTimeCasa());
            partida.marcarGol(jogador3, 87, 12, partida.getTimeCasa());

            partida.anularGol(partida.getGols().get(4));

            ArrayList<Gol> expectedGolsTimeFora = new ArrayList<>(Arrays.asList(gol2, gol3, gol4, gol5));
            ArrayList<Gol> expectedGolsTimeCasa = new ArrayList<>(Arrays.asList(gol1));

            assertEquals(expectedGolsTimeCasa, partida.getGols(partida.getTimeCasa()));
            assertEquals(expectedGolsTimeFora, partida.getGols(partida.getTimeVisitante()));

            assertEquals(1, partida.getQuantidadeGols(partida.getTimeCasa()));
            assertEquals(3, partida.getQuantidadeGols(partida.getTimeVisitante()));
        }
    }
}
