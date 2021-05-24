package com.interbank.spocktest.mock;

import com.interbank.spocktest.model.Desenvolvedor;

public class DesenvolvedorMock {

    public static Desenvolvedor buildDesenvolvedor() {
        return Desenvolvedor.builder()
                .idade(24)
                .nome("Joao")
                .email("joao@inter.com.br")
                .nivel(Desenvolvedor.Nivel.ESPECIALISTA)
                .salario(12000D)
                .build();
    }
}
