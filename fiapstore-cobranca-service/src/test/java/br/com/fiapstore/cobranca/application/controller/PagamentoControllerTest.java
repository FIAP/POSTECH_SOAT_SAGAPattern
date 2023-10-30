package br.com.fiapstore.cobranca.application.controller;

import br.com.fiapstore.cobranca.application.dto.PagamentoDto;
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
class PagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;


    private String PayloadNovoPagamento = "{\n  \"codigoPedido\": \"xxxxxxxx-xxxxxx-xxxx-xxx-xxx-xxx-xx\",\n  \"valor\": 2500,\n  \"percentualDesconto\": 10,\n  \"cpf\": \"29460519008\"\n}";
    @Test
    void realizarPagamento() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/pagamento")
                        .content(PayloadNovoPagamento)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void consultarPagamentoPorCodigo() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/pagamento")
                        .content(PayloadNovoPagamento)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        PagamentoDto dto = gson.fromJson(result.getResponse().getContentAsString(), PagamentoDto.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/pagamento/"+dto.getCodigo())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void cancelarPagamento() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/pagamento")
                        .content(PayloadNovoPagamento)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        PagamentoDto dto = gson.fromJson(result.getResponse().getContentAsString(), PagamentoDto.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/pagamento/cancelar/"+dto.getCodigo())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void confirmarPagamento() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/pagamento")
                        .content(PayloadNovoPagamento)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        PagamentoDto dto = gson.fromJson(result.getResponse().getContentAsString(), PagamentoDto.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/pagamento/confirmar/"+dto.getCodigo())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
}