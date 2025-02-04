package com.banco.xyz.financeiro.repository;

import com.banco.xyz.financeiro.model.TipoTransacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTransacaoRepository extends JpaRepository<TipoTransacao, Long> {
}
