package br.projetofinalwebservices.eduardondarocha.services;

import br.projetofinalwebservices.eduardondarocha.entities.Cidade;
import br.projetofinalwebservices.eduardondarocha.entities.Cliente;
import br.projetofinalwebservices.eduardondarocha.repositories.CidadeRepository;
import br.projetofinalwebservices.eduardondarocha.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }
    public Optional<Cliente> findById(Integer id){
        return clienteRepository.findById(id);
    }
    public void delete(Integer id){
        clienteRepository.deleteById(id);
    }
    public Cliente add(Cliente cliente){
        cliente.setId(null);
        Cidade cidadeAchadaPorNome = cidadeRepository.findByNome(cliente.getCidade().getNome()).get();
        cliente.setCidade(cidadeAchadaPorNome);
        return clienteRepository.save(cliente);
    }
    public Cliente update(Cliente cliente, Integer id){
        cliente.setId(id);
        return clienteRepository.save(cliente);
    }
}
