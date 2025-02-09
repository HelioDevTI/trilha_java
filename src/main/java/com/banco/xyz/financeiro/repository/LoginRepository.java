package com.banco.xyz.financeiro.repository;

import com.banco.xyz.financeiro.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {

    Optional<Login> findByEmail(String email);
}
