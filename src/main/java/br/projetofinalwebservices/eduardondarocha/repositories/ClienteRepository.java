package br.projetofinalwebservices.eduardondarocha.repositories;

import br.projetofinalwebservices.eduardondarocha.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
}
