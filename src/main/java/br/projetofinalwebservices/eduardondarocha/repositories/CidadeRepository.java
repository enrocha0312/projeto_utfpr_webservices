package br.projetofinalwebservices.eduardondarocha.repositories;

import br.projetofinalwebservices.eduardondarocha.entities.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
    Optional<Cidade> findByNome(String nome);
}
