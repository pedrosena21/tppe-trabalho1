package com.forca.app;

import java.lang.String;

public class App 
{
    private String palavra;

    public App (String palavra) {
        this.palavra = palavra;
    }

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

    public String calcularMascara(char letra) {
        char[] caracteres = this.palavra.toCharArray();

        for (int l = 0; l < caracteres.length; l++) {
            if (caracteres[l] != letra) {
                caracteres[l] = '*';
            }
        }

        return new String(caracteres);
    }

    public int calcularAcertos(char letra) {
        int acertos = this.palavra.length();

        for (int l = 0; l < this.palavra.length(); l++) {
            if (this.palavra.charAt(l)!= letra) {
                acertos--;
            }
        }

        return acertos;
    }
}
