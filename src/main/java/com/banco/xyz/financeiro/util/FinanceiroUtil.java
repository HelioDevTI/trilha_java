package com.banco.xyz.financeiro.util;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FinanceiroUtil {

    public static String formatarValor(String valor) {

        if(!valor.contains(".")){

            return valor + ",00";
        }


        // Expressão regular genérica para moedas ($ ou US$ ou R$)
        Pattern pattern = Pattern.compile("(\\$|US\\$|R\\$|€|¥)\\s*(\\d+(\\.\\d{1,2})?)");
        Matcher matcher = pattern.matcher(valor);

        if (matcher.find()) {
            String simboloMoeda = matcher.group(1);
            String valorNumerico = matcher.group(2);

            if (valorNumerico.contains(".")) {
                String[] partes = valorNumerico.split("\\.");
                String parteInteira = partes[0];
                String parteDecimal = partes[1];

                if (parteDecimal.length() == 1) {
                    parteDecimal += "0";
                }

                return simboloMoeda + parteInteira + "," + parteDecimal;
            } else {
                return simboloMoeda + valorNumerico + ",00";
            }
        } else {
            return valor; // Retorna a string original se não corresponder
        }
    }


    public static String converterDataString(LocalDateTime data){

        if(data == null){
            return "01/01/1000";
        }

        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public static void validarCPF(String cpf){

        cpf = cpf.replaceAll("[^\\d]", ""); // Remove caracteres não numéricos

        if (cpf.length() != 11) {
            throw new RuntimeException("CPF inválido");
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            throw new RuntimeException("CPF inválido"); // CPFs com todos os dígitos iguais são inválidos
        }

        int[] digitos = new int[11];
        for (int i = 0; i < 11; i++) {
            digitos[i] = Character.getNumericValue(cpf.charAt(i));
        }

        int digito1 = calcularDigitoVerificador(digitos, 9);
        int digito2 = calcularDigitoVerificador(digitos, 10);

        boolean resultado = digitos[9] == digito1 && digitos[10] == digito2;

        if(!resultado)
            throw new RuntimeException("CPF inválido");

    }

    private static int calcularDigitoVerificador(int[] digitos, int posicaoFinal) {
        int soma = 0;
        for (int i = 0; i < posicaoFinal; i++) {
            soma += digitos[i] * (posicaoFinal + 1 - i);
        }

        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }


}
