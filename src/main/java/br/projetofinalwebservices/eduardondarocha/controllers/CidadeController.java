package br.projetofinalwebservices.eduardondarocha.controllers;

import br.projetofinalwebservices.eduardondarocha.entities.Cidade;
import br.projetofinalwebservices.eduardondarocha.services.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("utfpr/cidades")
public class CidadeController {
    @Autowired
    private CidadeService cidadeService;
    @GetMapping
    public ResponseEntity<List<Cidade>> findAll(){
        return new ResponseEntity<>(cidadeService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Cidade>> findById(@PathVariable Integer id){
        return new ResponseEntity<>(
                Optional.of(cidadeService.findById(id).get()),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Cidade> add(@RequestBody Cidade cidade){
        return new ResponseEntity<>(cidadeService.add(cidade), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Cidade> update(@RequestBody Cidade cidade, @PathVariable Integer id){
        return new ResponseEntity<>(cidadeService.update(cidade, id),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id){
        cidadeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
