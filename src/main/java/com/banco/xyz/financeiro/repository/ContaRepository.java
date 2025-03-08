package com.banco.xyz.financeiro.repository;

import com.banco.xyz.financeiro.model.Conta;
import com.banco.xyz.financeiro.proxy.ContaUsuarioProxy;
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

    @Query("""
            SELECT co.id AS idConta,
                   u.nome AS nome,
                   u.cpf AS CPF,
                   l.email AS email,
                   CONCAT(co.numero,'-', co.digito) AS numero,
                   co.agencia AS agencia,
                   CONCAT('R$', co.saldo) AS saldo
            FROM Usuario u,
                 Login l,
                 Conta co
            WHERE u.id = :idUsuario
            AND l.idUsuario = :idUsuario
            AND co.idUsuario = :idUsuario
            """)
    ContaUsuarioProxy consultaContaUsaurio(Long idUsuario);
}
