package br.projetofinalwebservices.eduardondarocha.services;

import br.projetofinalwebservices.eduardondarocha.entities.Cidade;
import br.projetofinalwebservices.eduardondarocha.repositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {
    @Autowired
    private CidadeRepository cidadeRepository;
    public List<Cidade> findAll(){
        return cidadeRepository.findAll();
    }
    public Optional<Cidade> findById(Integer id){
        return cidadeRepository.findById(id);
    }
    public void delete(Integer id){
        cidadeRepository.deleteById(id);
    }
    public Cidade add(Cidade cidade){
        cidade.setId(null);
        return cidadeRepository.save(cidade);
    }
    public Cidade update(Cidade cidade, Integer id){
        cidade.setId(id);
        return cidadeRepository.save(cidade);
    }
}
