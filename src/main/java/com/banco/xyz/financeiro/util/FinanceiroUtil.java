package com.banco.xyz.financeiro.util;

import org.springframework.format.annotation.DateTimeFormat;

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


}
