package com.interbank.spocktest.service

import com.interbank.spocktest.model.Desenvolvedor
import com.interbank.spocktest.repository.DesenvolvedorRepository
import spock.lang.*
import com.interbank.spocktest.mock.DesenvolvedorMock;

class DesenvolvedorServiceSpecification extends Specification {

    DesenvolvedorRepository desenvolvedorRepository = Mock();
    DesenvolvedorService desenvolvedorService = new DesenvolvedorService(desenvolvedorRepository);


    def"scenario 1 - Criar um desenvolvedor com sucesso"() {

        given:_ "dado um payload regular"
        def desenvolvedor = DesenvolvedorMock.buildDesenvolvedor();

        when:_ "quando eu criar um novo desenvolvedor"
        def desenvolvedorPersistido = desenvolvedorService.create(desenvolvedor)
        desenvolvedor.id = 1

        then:_ "entao deve criar um novo desenvolvedor"
        desenvolvedorPersistido == desenvolvedor
        1 * desenvolvedorRepository.findDesenvolvedorByEmail(_ as String) >> Optional.empty()
        1 * desenvolvedorRepository.save(_ as Desenvolvedor) >> {
            arg ->
                def dev = (arg.get(0) as Desenvolvedor);
                dev.id = 1;
                return dev;
        }
    }
}




