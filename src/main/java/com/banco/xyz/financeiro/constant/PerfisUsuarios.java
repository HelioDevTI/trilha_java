package com.banco.xyz.financeiro.constant;

public class PerfisUsuarios {

    public static final String CORRENTISTA = "hasAnyAuthority('CORRENTISTA')";
    public static final String GERENTE = "hasAnyAuthority('GERENTE')";
    public static final String ADMINISTRADOR = "hasAnyAuthority('ADMINISTRADOR')";
    public static final String CORRENTISTA_GERENTE = "hasAnyAuthority('CORRENTISTA', 'GERENTE')";
    public static final String CORRENTISTA_ADMINISTRADOR = "hasAnyAuthority('CORRENTISTA', 'ADMINISTRADOR')";
    public static final String GERENTE_ADMINISTRADOR = "hasAnyAuthority('GERENTE', 'ADMINISTRADOR')";
    public static final String GERENTE_ADMINISTRADOR_CORRENTISTA = "hasAnyAuthority('GERENTE', 'ADMINISTRADOR', 'CORRENTISTA')";
}
