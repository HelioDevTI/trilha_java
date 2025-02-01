package com.banco.xyz.financeiro.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "conta")
@Data
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "usuario_id")
    private Long idUsuario;

    @Column(name = "numero")
    private Long numero;

    @Column(name = "digito")
    private Long digito;

    @Column(name = "agencia")
    private Long agencia;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;


}
