package com.cadastropessoas.controller;



import com.cadastropessoas.exceptions.IdInvalidoException;
import com.cadastropessoas.exceptions.IdNaoEncontradoException;
import com.cadastropessoas.model.PessoaDto;
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
    public ResponseEntity<List<PessoaDto>> lista(@RequestParam(required = false) String nome) {

        List<PessoaDto> pessoas;
        if (nome == null) {
            pessoas = repository.findAll();
        } else {
            pessoas = repository.findByNome(nome);
        }

        return ResponseEntity.ok().body(pessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDto> buscarPeloId(@PathVariable Long id) throws IdNaoEncontradoException {

        Optional<PessoaDto> pessoaDtoOptional = repository.findById(id);
        PessoaDto pessoaDto = pessoaDtoOptional.orElseThrow(() -> new IdNaoEncontradoException("Pessoa não encontrada com id: " + id));

        return ResponseEntity.ok().body(pessoaDto);
    }

    @PostMapping
    public ResponseEntity<PessoaDto> create(@RequestBody @Valid PessoaDto pessoaDto) throws IdInvalidoException {

        if (pessoaDto.getId() != null) {
            throw new IdInvalidoException("O id não deve ser enviado na criação.");
        }

        PessoaDto pessoaDtoSalva = repository.save(pessoaDto);
        return ResponseEntity.ok().body(pessoaDtoSalva);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<PessoaDto> deletarPessoa(@PathVariable Long id) throws IdNaoEncontradoException {


        Optional<PessoaDto> pessoaDtoOptional = repository.findById(id);
        PessoaDto pessoaDto = pessoaDtoOptional.orElseThrow(() -> new IdNaoEncontradoException("Pessoa não encontrada com id: " + id));

        repository.deleteById(id);
        return ResponseEntity.ok().body(pessoaDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updatePessoa(@PathVariable("id") Long id, @RequestBody @Valid PessoaDto pessoaDto) throws IdNaoEncontradoException {

        Optional<PessoaDto> pessoaDtoOptional = repository.findById(id);
        PessoaDto pessoaDtoAtual = pessoaDtoOptional.orElseThrow(() -> new IdNaoEncontradoException("Pessoa não encontrada com id: " + id));

        pessoaDtoAtual.setNome(pessoaDto.getNome());
        pessoaDtoAtual.setCpf(pessoaDto.getCpf());
        pessoaDtoAtual.setIdade(pessoaDto.getIdade());

        repository.save(pessoaDtoAtual);
        return ResponseEntity.ok().build();
    }

}
