package br.projetofinalwebservices.eduardondarocha.controllers;

import br.projetofinalwebservices.eduardondarocha.entities.Cliente;
import br.projetofinalwebservices.eduardondarocha.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("utfpr/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    @GetMapping
    public ResponseEntity<List<Cliente>> findAll(){
        return new ResponseEntity<>(clienteService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Cliente>> findById(@PathVariable Integer id){
        return new ResponseEntity<>(
                Optional.of(clienteService.findById(id).get()),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Cliente> add(@RequestBody Cliente cliente){
        return new ResponseEntity<>(clienteService.add(cliente), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@RequestBody Cliente cliente, @PathVariable Integer id){
        return new ResponseEntity<>(clienteService.update(cliente, id),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        clienteService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
