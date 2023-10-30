package br.com.fiapstore.pedido.application.controller;

import br.com.fiapstore.pedido.application.dto.PedidoDto;
import br.com.fiapstore.pedido.domain.entity.StatusPedido;
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
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    private String payload1="{\n" + "  \"cpf\": \"73261578041\",\n" + "  \"codigoCupom\": \"TESTE100\",\n" + "  \"codigoProduto\": 1,\n" + "  \"quantidadeProduto\": 3\n" + "}";

    @Test
    void deveCriarUmPedido() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/pedido")
                        .content(payload1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    void deveConfirmarUmPedido() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/pedido")
                        .content(payload1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        PedidoDto dto = gson.fromJson(result.getResponse().getContentAsString(), PedidoDto.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/pedido/confirmar/"+dto.getCodigoPedido())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

    }
    @Test
    void deveCancelarUmPedido() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/pedido")
                        .content(payload1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        PedidoDto dto = gson.fromJson(result.getResponse().getContentAsString(), PedidoDto.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/pedido/cancelar/"+dto.getCodigoPedido())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

    }
}