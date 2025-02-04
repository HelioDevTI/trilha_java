package com.banco.xyz.financeiro.model;

import com.banco.xyz.financeiro.enumeration.Tipo;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "tipo_transacao")
@Data
public class TipoTransacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "moeda")
    private String moeda;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "ativo")
    private Boolean ativo;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Column(name = "dataCriacao")
    private LocalDateTime dataCriacao;

}
