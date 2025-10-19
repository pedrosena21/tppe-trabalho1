package com.campeonato;

import java.time.LocalDateTime;

public class Gol {
    private Time levou;
    private LocalDateTime tempo;
    private Jogador jogador;
    private boolean anulado = false;

    public boolean isAnulado() {
        return anulado;
    }

    public boolean isGolContra() {
        return getFez() == levou;
    }

    public Time getFez() {
        return jogador.getTime();
    }

    public Time getLevou() {
        return levou;
    }

    public LocalDateTime getTempo() {
        return tempo;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public Gol(Jogador jogador, LocalDateTime tempo, Time levou) {
        this.levou = levou;
        this.jogador = jogador;
        this.tempo = tempo != null ? tempo : LocalDateTime.now();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((levou == null) ? 0 : levou.hashCode());
        result = prime * result + ((tempo == null) ? 0 : tempo.hashCode());
        result = prime * result + ((jogador == null) ? 0 : jogador.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Gol other = (Gol) obj;
        
        if (levou == null) {
            if (other.levou != null)
                return false;
        } else if (!levou.equals(other.levou))
            return false;
        if (tempo == null) {
            if (other.tempo != null)
                return false;
        } else if (!tempo.equals(other.tempo))
            return false;
        if (jogador == null) {
            if (other.jogador != null)
                return false;
        } else if (!jogador.equals(other.jogador))
            return false;
        return true;
    }

    public void anularGol() {
        this.anulado = true;
    }
}
