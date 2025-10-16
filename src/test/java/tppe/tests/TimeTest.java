package tppe.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.campeonato.Time;


class timeTest {
    private Time timeTest;

    @BeforeEach
    void setUp() {
        timeTest = new Time("Santos", "Vila Belmiro");
    }

    @Test
    void testVitoria(){
        timeTest.atualizarEstatisticas(3, 1, 0, 0);
        assertEquals(3, timeTest.getPontos());
        assertEquals(1, timeTest.getVitorias());
        assertEquals(0, timeTest.getEmpates());
        assertEquals(0, timeTest.getDerrotas());
        assertEquals(3, timeTest.getGolsMarcados());
        assertEquals(1, timeTest.getGolsSofridos());
        assertEquals(2, timeTest.getSaldoGols());
    }

    @Test
    void testEmpate(){
        timeTest.atualizarEstatisticas(2, 2, 0, 0);
        assertEquals(1, timeTest.getPontos());
        assertEquals(0, timeTest.getVitorias());
        assertEquals(1, timeTest.getEmpates());
        assertEquals(0, timeTest.getDerrotas());
        assertEquals(2, timeTest.getGolsMarcados());
        assertEquals(2, timeTest.getGolsSofridos());
        assertEquals(0, timeTest.getSaldoGols());
    }

    @Test
    void testDerrota(){
        timeTest.atualizarEstatisticas(0, 1, 0, 0);
        assertEquals(0, timeTest.getPontos());
        assertEquals(0, timeTest.getVitorias());
        assertEquals(0, timeTest.getEmpates());
        assertEquals(1, timeTest.getDerrotas());
        assertEquals(0, timeTest.getGolsMarcados());
        assertEquals(1, timeTest.getGolsSofridos());
        assertEquals(-1, timeTest.getSaldoGols());
    }
}