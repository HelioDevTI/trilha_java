package com.banco.xyz.financeiro.repository;

import com.banco.xyz.financeiro.model.Transacao;
import com.banco.xyz.financeiro.proxy.DadosTrasacaoProxy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query("""
            SELECT co.numero AS numero,
                   co.digito AS digito,
                   co.agencia AS agencia,
                   tr.dataTransacao AS data,
                   ti.descricao AS tipoTransacao,
                   tr.descricao AS descricao,
                   CONCAT(ti.moeda, tr.valor) AS valor,
                   CONCAT('R$', tr.valor) AS valorConvertido
            FROM Conta co,
                 Transacao tr,
                 TipoTransacao ti
            WHERE co.idUsuario = :idConta
            AND tr.idConta = co.id
            AND ti.id = tr.idTipo
            AND ( :dataInicio IS NULL OR DATE(tr.dataTransacao) BETWEEN :dataInicio AND :dataFim)
            AND ( :tipoTransacao IS NULL OR ti.descricao = :tipoTransacao)
            AND ( :descCompra IS NULL OR tr.descricao LIKE %:descCompra%)
            ORDER BY tr.dataTransacao
            """)
    List<DadosTrasacaoProxy> consultaTransacao(Long idConta, LocalDate dataInicio, LocalDate dataFim, String tipoTransacao,
                                               String descCompra);
}
