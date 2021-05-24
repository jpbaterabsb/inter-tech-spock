package com.interbank.spocktest.repository;

import com.interbank.spocktest.model.Desenvolvedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DesenvolvedorRepository extends JpaRepository<Desenvolvedor, Long> {

    Optional<Desenvolvedor> findDesenvolvedorByEmail(String email);
    Optional<Desenvolvedor> findDesenvolvedorByEmailAndIdNot(String email, Long id);
}
