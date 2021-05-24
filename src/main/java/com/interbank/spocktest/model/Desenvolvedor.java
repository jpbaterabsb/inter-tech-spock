package com.interbank.spocktest.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Desenvolvedor {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "o campo nome é obrigatorio")
    private String nome;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "o campo nivel é obrigatório")
    private Nivel nivel;

    @NotNull(message = "o campo salário não é obrigatório")
    private Double salario;

    @NotBlank(message = "o campo email é obrigatório")
    private String email;

    @NotNull(message = "a idade é obrigatória")
    private Integer idade;

    @RequiredArgsConstructor
    public enum Nivel {
        JUNIOR(1000.00, 3000.00),
        PLENO(3000.01, 6000.00),
        SENIOR(6000.01, 11000.00),
        ESPECIALISTA(11000.00, 15000.00);


        private final Double base;
        private final Double teto;



        public boolean estaDentroDaFaixaSalarial(Double salario) {
            return this.base <= salario && salario <=  this.teto;
        }
    }
}
