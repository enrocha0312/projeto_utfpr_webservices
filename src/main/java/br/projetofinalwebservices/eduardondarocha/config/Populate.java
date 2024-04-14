package br.projetofinalwebservices.eduardondarocha.config;

import br.projetofinalwebservices.eduardondarocha.entities.Cidade;
import br.projetofinalwebservices.eduardondarocha.repositories.CidadeRepository;
import br.projetofinalwebservices.eduardondarocha.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration

public class Populate implements CommandLineRunner {
    @Autowired
    private CidadeRepository cidadeRepository;
    @Override
    public void run(String... args) throws Exception {
        Cidade cidade1 = Cidade.builder()
                .id(null)
                .nome("Petrópolis").build();
        Cidade cidade2 = Cidade.builder()
                .id(null)
                .nome("Teresópolis").build();
        Cidade cidade3 = Cidade.builder()
                .id(null)
                .nome("Nova Friburgo").build();
        Cidade cidade4 = Cidade.builder()
                .id(null)
                .nome("Itatiaia").build();
        cidadeRepository.saveAll(Stream.of(cidade1, cidade2, cidade3, cidade4).collect(Collectors.toList()));
    }
}
