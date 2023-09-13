package com.cadastropessoas.repository;

import com.cadastropessoas.model.PessoaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaDto, Long> {
    ;
    List<PessoaDto> findByNome(String nome);

    Optional<PessoaDto> findById(Long id);

}
