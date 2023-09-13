package com.cadastropessoas.controller;


import com.cadastropessoas.model.Pessoa;
import com.cadastropessoas.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/pessoa")
public class CadastroController {

    @Autowired
    private PessoaRepository repository;

    @GetMapping
    public List<Pessoa> lista(String nome) {

        return repository.findAll();
    }

    @PostMapping
    public void create(@RequestBody Pessoa pessoa) {

        repository.save(pessoa);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Pessoa> optional = repository.findById(id);
        if (optional.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody @Valid Pessoa pessoa) {
//        Optional<Pessoa> optional = repository.findById(id);
//        if (optional.isPresent()) {
//            pessoa.setId(id);
//            repository.save(pessoa);
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.notFound().build();
//    }

        return repository.findById(id)
                .map(record -> {
                    record.setNome(pessoa.getNome());
                    record.setCpf(pessoa.getCpf());
                    record.setIdade(pessoa.getIdade());
                    Pessoa updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());

    }

}
