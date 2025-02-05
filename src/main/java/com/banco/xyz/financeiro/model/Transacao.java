package com.banco.xyz.financeiro.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacao")
@Data
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tipo_id")
    private Long idTipo;

    @Column(name = "conta_id")
    private Long idConta;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "valor_convertido")
    private BigDecimal valorConvertido;

    @Column(name = "data_transacao")
    private LocalDateTime dataTransacao;

}
