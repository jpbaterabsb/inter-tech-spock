package com.interbank.spocktest.service;

import com.interbank.spocktest.exception.ValidationException;
import com.interbank.spocktest.model.Desenvolvedor;
import com.interbank.spocktest.repository.DesenvolvedorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.interbank.spocktest.mock.DesenvolvedorMock.buildDesenvolvedor;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DesenvolvedorServiceTest {

    @Mock
    DesenvolvedorRepository desenvolvedorRepository;


    @InjectMocks
    DesenvolvedorService desenvolvedorService;

    @Test
    void given_um_payload_regular_when_eu_criar_um_novo_desenvolvedor_then_deve_salvar() {
        when(desenvolvedorRepository.save(any(Desenvolvedor.class))).then(a -> {
            Desenvolvedor dev = a.getArgument(0);
            dev.setId(1L);
            return dev;
        });
        when(desenvolvedorRepository.findDesenvolvedorByEmail(any())).thenReturn(Optional.empty());
        Desenvolvedor desenvolvedor = desenvolvedorService.create(buildDesenvolvedor());
        Assertions.assertEquals(1L, desenvolvedor.getId());
        Assertions.assertEquals(24, desenvolvedor.getIdade());
        Assertions.assertEquals("Joao", desenvolvedor.getNome());
        Assertions.assertEquals("joao@inter.com.br", desenvolvedor.getEmail());
        Assertions.assertEquals(Desenvolvedor.Nivel.ESPECIALISTA, desenvolvedor.getNivel());
        Assertions.assertEquals(12000D, desenvolvedor.getSalario());
    }


    @Test
    void given_um_payload_com_idade_menor_que_18_when_eu_criar_um_novo_desenvolvedor_then_deve_lancar_erro() {
        Desenvolvedor mockDesenvolvedor = buildDesenvolvedor();
        mockDesenvolvedor.setIdade(14);
        ValidationException validationException = assertThrows(ValidationException.class, () -> desenvolvedorService.create(mockDesenvolvedor));
        assertEquals("O desenvolvedor deve ser maior de idade", validationException.getMessage());
    }

    @Test
    void given_um_payload_com_salario_menor_do_que_o_cargo_when_eu_criar_um_novo_desenvolvedor_then_deve_lancar_erro() {
        Desenvolvedor mockDesenvolvedor = buildDesenvolvedor();
        mockDesenvolvedor.setSalario(8000D);
        ValidationException validationException = assertThrows(ValidationException.class, () -> desenvolvedorService.create(mockDesenvolvedor));
        assertEquals( "Faixa salarial inválida para este nivel", validationException.getMessage());
    }

    @Test
    void given_um_payload_com_um_email_ja_existente_when_eu_criar_um_novo_desenvolvedor_then_deve_lancar_erro() {
        Desenvolvedor mockDesenvolvedor = buildDesenvolvedor();
        when(desenvolvedorRepository.findDesenvolvedorByEmail(any())).thenReturn(Optional.of(buildDesenvolvedor()));
        ValidationException validationException = assertThrows(ValidationException.class, () -> desenvolvedorService.create(mockDesenvolvedor));
        assertEquals( "Este e-mail já esta cadastrado em nossa plataforma", validationException.getMessage());
    }

    @Test
    void update() {
        fail();
    }

    @Test
    void findAll() {
        fail();
    }

    @Test
    void testFindAll() {
        fail();
    }
}