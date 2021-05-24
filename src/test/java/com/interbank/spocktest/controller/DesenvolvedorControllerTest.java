package com.interbank.spocktest.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interbank.spocktest.constants.AppConstants;
import com.interbank.spocktest.model.Desenvolvedor;
import com.interbank.spocktest.repository.DesenvolvedorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import static com.interbank.spocktest.utils.TestUtils.extractJsonToPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DesenvolvedorControllerTest {

    @Autowired
    private DesenvolvedorRepository desenvolvedorRepository;


    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    @Rollback
    @Transactional
    public void dado_um_payload_invalido_quando_executar_uma_cricao_entao_deve_lancar_erro_de_validacao_de_payload() throws Exception {
        String request = extractJsonToPath("json/dado_um_payload_invalido_quando_executar_uma_cricao_entao_deve_lancar_erro_de_validacao_de_payload_request.json");
        String expectedResponseString = extractJsonToPath("json/dado_um_payload_invalido_quando_executar_uma_cricao_entao_deve_lancar_erro_de_validacao_de_payload_response.json");
        MvcResult mvcResult = this.mockMvc
                .perform(
                        post(AppConstants.DESENVOLVEDOR_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(request)
                ).andDo(print()).andExpect(status().isBadRequest())
                .andReturn();

        JsonNode response = mapper.readTree(mvcResult.getResponse().getContentAsByteArray());
        JsonNode expectedResponse = mapper.readTree(expectedResponseString);

        Assertions.assertEquals(expectedResponse, response);
    }

    @Test
    @Rollback
    @Transactional
    public void dado_um_payload_com_idade_menor_que_18_anos_quando_executar_uma_cricao_entao_deve_lancar_erro_para_menor_18_anos() throws Exception {
        String request = extractJsonToPath("json/dado_um_payload_com_idade_menor_que_18_anos_quando_executar_uma_cricao_entao_deve_lancar_erro_para_menor_18_anos_request.json");
        String expectedResponseString = extractJsonToPath("json/dado_um_payload_com_idade_menor_que_18_anos_quando_executar_uma_cricao_entao_deve_lancar_erro_para_menor_18_anos_response.json");
        MvcResult mvcResult = this.mockMvc
                .perform(
                        post(AppConstants.DESENVOLVEDOR_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(request)
                ).andDo(print()).andExpect(status().isBadRequest())
                .andReturn();

        JsonNode response = mapper.readTree(mvcResult.getResponse().getContentAsByteArray());
        JsonNode expectedResponse = mapper.readTree(expectedResponseString);

        Assertions.assertEquals(expectedResponse, response);
    }

    @Test
    @Rollback
    @Transactional
    public void dado_um_payload_com_salario_invalido_para_o_cargo_quando_executar_uma_cricao_entao_deve_lancar_erro_invalido_para_o_cargo() throws Exception {
        String request = extractJsonToPath("json/dado_um_payload_com_salario_invalido_para_o_cargo_quando_executar_uma_cricao_entao_deve_lancar_erro_invalido_para_o_cargo_request.json");
        String expectedResponseString = extractJsonToPath("json/dado_um_payload_com_salario_invalido_para_o_cargo_quando_executar_uma_cricao_entao_deve_lancar_erro_invalido_para_o_cargo_response.json");
        MvcResult mvcResult = this.mockMvc
                .perform(
                        post(AppConstants.DESENVOLVEDOR_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(request)
                ).andDo(print()).andExpect(status().isBadRequest())
                .andReturn();

        JsonNode response = mapper.readTree(mvcResult.getResponse().getContentAsByteArray());
        JsonNode expectedResponse = mapper.readTree(expectedResponseString);

        Assertions.assertEquals(expectedResponse, response);
    }

    @Test
    @Rollback
    @Transactional
    public void dado_um_payload_com_email_ja_existente_quando_executar_uma_cricao_entao_deve_lancar_erro_email_ja_existente() throws Exception {
        String request = extractJsonToPath("json/dado_um_payload_com_email_ja_existente_quando_executar_uma_cricao_entao_deve_lancar_erro_email_ja_existente_request.json");
        String expectedResponseString = extractJsonToPath("json/dado_um_payload_com_email_ja_existente_quando_executar_uma_cricao_entao_deve_lancar_erro_email_ja_existente_response.json");

        desenvolvedorRepository.save(mapper.readValue(request, Desenvolvedor.class));

        MvcResult mvcResult = this.mockMvc
                .perform(
                        post(AppConstants.DESENVOLVEDOR_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(request)
                ).andDo(print()).andExpect(status().isBadRequest())
                .andReturn();

        JsonNode response = mapper.readTree(mvcResult.getResponse().getContentAsByteArray());
        JsonNode expectedResponse = mapper.readTree(expectedResponseString);

        Assertions.assertEquals(expectedResponse, response);
    }

    @Test
    @Rollback
    @Transactional
    public void dado_um_payload_valido_quando_executar_uma_cricao_entao_deve_retornar_payload_com_id() throws Exception {
        String request = extractJsonToPath("json/dado_um_payload_valido_quando_executar_uma_cricao_entao_deve_retornar_payload_com_id_request.json");
        String expectedResponseString = extractJsonToPath("json/dado_um_payload_valido_quando_executar_uma_cricao_entao_deve_retornar_payload_com_id_response.json");

        MvcResult mvcResult = this.mockMvc
                .perform(
                        post(AppConstants.DESENVOLVEDOR_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(request)
                ).andDo(print()).andExpect(status().isCreated())
                .andReturn();

        JsonNode response = mapper.readTree(mvcResult.getResponse().getContentAsByteArray());
        JsonNode expectedResponse = mapper.readTree(expectedResponseString);

        Assertions.assertEquals(expectedResponse, response);
    }
}