package com.banco.xyz.financeiro.repository;

import com.banco.xyz.financeiro.model.Transacao;
import com.banco.xyz.financeiro.proxy.DadosMetricasTransacaoProxy;
import com.banco.xyz.financeiro.proxy.DadosRelatorioTransacaoProxy;
import com.banco.xyz.financeiro.proxy.DadosTransacaoProxy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    List<DadosTransacaoProxy> consultaTransacao(Long idUsuario, LocalDate dataInicio, LocalDate dataFim, String tipoTransacao,
                                                String descCompra);

    @Query("""
            SELECT tr.dataTransacao AS data,
                   ti.descricao AS tipoTransacao,
                   ti.tipo AS tipoCobranca,
                   tr.descricao AS descricao,
                   CONCAT(ti.moeda, tr.valor) AS valorCompra,
                   CONCAT('R$', tr.valor) AS valorReais
            FROM Transacao tr,
                 TipoTransacao ti
            WHERE tr.idConta = idConta
            AND ti.id = tr.idTipo
            """)
    List<DadosRelatorioTransacaoProxy> consultaTransacaoConta(Long idConta);

    @Query("""
            SELECT t.idTipo AS codigo,
                   tt.moeda AS moeda,
                   tt.tipo AS transacao,
                   tt.descricao AS tipo,
                   COUNT(t.id) AS quantidade,
                   SUM(t.valorConvertido) AS total,
                   ROUND(COUNT(t.id) * 100.0 / (SELECT COUNT(*)
                                                FROM Transacao q
                                                WHERE q.dataTransacao BETWEEN :iniPeriodo AND :fimPeriodo
                                                AND q.idConta = :idConta), 2) AS porcentagem
            FROM Transacao t,
                 TipoTransacao tt
            WHERE tt.id = t.idTipo
            AND t.dataTransacao BETWEEN :iniPeriodo AND :fimPeriodo
            AND t.idConta = :idConta
            GROUP BY t.idTipo
            """)
    List<DadosMetricasTransacaoProxy> consutaMetricasTrasacoes(Long idConta,
                                                               LocalDateTime iniPeriodo,
                                                               LocalDateTime fimPeriodo);

}
