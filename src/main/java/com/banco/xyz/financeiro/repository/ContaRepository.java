package com.banco.xyz.financeiro.repository;

import com.banco.xyz.financeiro.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    @Query("""
            SELECT c.id
            FROM Conta c,
                 Usuario u,
                 Login l
            WHERE u.id = c.idUsuario
            AND l.idUsuario = u.id
            AND u.nome = :nome
            AND l.email = :email
            """)
    Long idContaUsuarioEmail(String nome, String email);
}
