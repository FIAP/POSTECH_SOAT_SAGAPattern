package br.com.fiapstore.entrega.application.controller;

import br.com.fiapstore.entrega.application.dto.EntregaDto;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EntregaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    private String payload = "{\n  \"codigoPedido\": \"xxxxxxxx-xxxxxx-xxxx-xxx-xxx-xxx-xx\",\n  \"cpf\": \"29460519008\"\n}";

    @Test
    void agendarEntrega() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/entrega")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void confirmarEntrega() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/entrega")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        EntregaDto dto = gson.fromJson(result.getResponse().getContentAsString(), EntregaDto.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/entrega/confirmar/"+dto.getCodigo())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    void cancelarEntrega() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/entrega")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        EntregaDto dto = gson.fromJson(result.getResponse().getContentAsString(), EntregaDto.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/entrega/cancelar/"+dto.getCodigo())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

    }
}