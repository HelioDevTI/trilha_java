package com.banco.xyz.financeiro.repository;

import com.banco.xyz.financeiro.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    @Query("""
            SELECT p.id
            FROM Perfil p
            WHERE p.tipo = :tipoPerfil
            """)
    Long idPerfilTipo(String tipoPerfil);
}
