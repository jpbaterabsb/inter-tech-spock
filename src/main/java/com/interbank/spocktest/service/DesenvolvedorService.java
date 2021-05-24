package com.interbank.spocktest.service;

import com.interbank.spocktest.exception.ValidationException;
import com.interbank.spocktest.model.Desenvolvedor;
import com.interbank.spocktest.repository.DesenvolvedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DesenvolvedorService {

    private final DesenvolvedorRepository repository;


    public Desenvolvedor create(Desenvolvedor desenvolvedor) {
        validarParaCriarDesenvolvedor(desenvolvedor);
        return repository.save(desenvolvedor);
    }

    public void update(Long id, Desenvolvedor desenvolvedor) {
        desenvolvedor.setId(id);
        validarParaAtualizarDesenvolvedor(desenvolvedor);
        repository.save(desenvolvedor);
    }

    public Optional<Desenvolvedor> findAll(Long id) {
        return repository.findById(id);
    }

    public List<Desenvolvedor> findAll() {
        return repository.findAll();
    }

    private void validarParaCriarDesenvolvedor(Desenvolvedor desenvolvedor) {
        realizarValidacaoSimples(desenvolvedor);
        if (repository.findDesenvolvedorByEmail(desenvolvedor.getEmail()).isPresent()) {
            throw new ValidationException("Este e-mail já esta cadastrado em nossa plataforma");
        }
    }

    private void validarParaAtualizarDesenvolvedor(Desenvolvedor desenvolvedor) {
        realizarValidacaoSimples(desenvolvedor);
        if (repository.findDesenvolvedorByEmailAndIdNot(desenvolvedor.getEmail(), desenvolvedor.getId()).isPresent()) {
            throw new ValidationException("Este e-mail já esta cadastrado em nossa plataforma");
        }
    }

    private void realizarValidacaoSimples(Desenvolvedor desenvolvedor) {
        if (desenvolvedor.getIdade() < 18) {
            throw new ValidationException("O desenvolvedor deve ser maior de idade");
        }

        if (!desenvolvedor.getNivel().estaDentroDaFaixaSalarial(desenvolvedor.getSalario())) {
            throw new ValidationException("Faixa salarial inválida para este nivel");
        }
    }
}
