package com.interbank.spocktest.controller

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.interbank.spocktest.constants.AppConstants
import com.interbank.spocktest.model.Desenvolvedor
import com.interbank.spocktest.repository.DesenvolvedorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import spock.lang.Specification
import spock.lang.Unroll

import javax.transaction.Transactional

import static com.interbank.spocktest.utils.TestUtils.extractJsonToPath
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class DesenvolvedorControllerSpecification extends Specification {

    @Autowired
    private DesenvolvedorRepository desenvolvedorRepository;


    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper mapper = new ObjectMapper();

    @Rollback
    @Transactional
    @Unroll
    def "Scenario - #scenario"() throws Exception {
        String request = extractJsonToPath("json/${payload}_request.json");
        String expectedResponseString = extractJsonToPath("json/${payload}_response.json");
        when:
        preConfiguration(desenvolvedorRepository, request)
        MvcResult mvcResult = this.mockMvc
                .perform(
                        post(AppConstants.DESENVOLVEDOR_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(request)
                )
//                .andDo(print())
                .andExpect(expectedStatus)
                .andReturn();

        JsonNode response = mapper.readTree(mvcResult.getResponse().getContentAsByteArray());
        JsonNode expectedResponse = mapper.readTree(expectedResponseString);

        then:
        expectedResponse == response

        where:
        payload                                                                                                                     | scenario                                                                                                                                          | expectedStatus          | preConfiguration
        "dado_um_payload_valido_quando_executar_uma_cricao_entao_deve_retornar_payload_com_id"                                      | "Dado um payload valido quando eu criar um desenvolvedor entao deve retornar o mesmo payload com o id"                                            | status().isCreated()    | defaultPreconfiguration
        "dado_um_payload_invalido_quando_executar_uma_cricao_entao_deve_lancar_erro_de_validacao_de_payload"                        | "Dado um payload invalido quando eu criar um desenvolvedor entao deve lançar um na validação do payload"                                          | status().isBadRequest() | defaultPreconfiguration
        "dado_um_payload_com_idade_menor_que_18_anos_quando_executar_uma_cricao_entao_deve_lancar_erro_para_menor_18_anos"          | "Dado um payload com a idade menor que 18 anos quando eu criar um desenvolvedor entao deve lançar um erro relacionado a idade"                    | status().isBadRequest() | defaultPreconfiguration
        "dado_um_payload_com_salario_invalido_para_o_cargo_quando_executar_uma_cricao_entao_deve_lancar_erro_invalido_para_o_cargo" | "Dado um payload com o salario invalido para o cargo quando eu criar um desenvolvedor entao deve lançar um erro relacionado ao cargo e o salario" | status().isBadRequest() | defaultPreconfiguration
        "dado_um_payload_com_email_ja_existente_quando_executar_uma_cricao_entao_deve_lancar_erro_email_ja_existente"               | "Dado um payload com um email ja existente quando eu criar um desenvolvedor entao deve lançar um erro relacionado a e-mail duplicado"             | status().isBadRequest() | preConfigurationEmailJaExistent
    }

    def static preConfigurationEmailJaExistent = {
        repository,String request ->
        repository.save(mapper.readValue(request, Desenvolvedor));
        return false;
    }

    def static defaultPreconfiguration = {
        repository,String request ->
            return false;
    }
}
