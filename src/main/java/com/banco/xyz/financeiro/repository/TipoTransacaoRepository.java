package com.banco.xyz.financeiro.repository;

import com.banco.xyz.financeiro.model.TipoTransacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTransacaoRepository extends JpaRepository<TipoTransacao, Long> {

    @Query("""
            SELECT t.id
            FROM TipoTransacao t
            WHERE t.moeda = :moeda
            AND t.descricao = :tipo
            """)
   Long idTipoMoedaDescricao(String moeda, String tipo);
}
