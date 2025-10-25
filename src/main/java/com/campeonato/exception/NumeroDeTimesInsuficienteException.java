package com.campeonato.exception;

public class NumeroDeTimesInsuficienteException extends Exception {
    public NumeroDeTimesInsuficienteException() {
        super("O número de times cadastrados no campeonato precisa ser 20!");
    }
}
