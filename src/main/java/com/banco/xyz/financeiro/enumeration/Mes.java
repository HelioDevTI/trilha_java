package com.banco.xyz.financeiro.enumeration;

import java.time.Month;

public enum Mes {

    JANEIRO(Month.JANUARY),
    FEVEREIRO(Month.FEBRUARY),
    MARCO(Month.MARCH),
    ABRIL(Month.APRIL),
    MAIO(Month.MAY),
    JUNHO(Month.JUNE),
    JULHO(Month.JULY),
    AGOSTO(Month.AUGUST),
    SETEMBRO(Month.SEPTEMBER),
    OUTUBRO(Month.OCTOBER),
    NOVEMBRO(Month.NOVEMBER),
    DEZEMBRO(Month.DECEMBER);


    private final Month mesJavaTime;

    Mes(Month mesJavaTime) {
        this.mesJavaTime = mesJavaTime;
    }

    public Month getMesJavaTime() {
        return mesJavaTime;
    }

}
