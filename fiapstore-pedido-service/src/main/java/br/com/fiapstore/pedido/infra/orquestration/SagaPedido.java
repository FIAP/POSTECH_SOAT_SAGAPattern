package br.com.fiapstore.pedido.infra.orquestration;

import br.com.fiapstore.pedido.application.usecase.ConfirmarPedido;
import br.com.fiapstore.pedido.domain.exception.OperacaoInvalidaException;
import br.com.fiapstore.pedido.domain.exception.PedidoNaoEncontradoException;
import br.com.fiapstore.pedido.domain.exception.PercentualDescontoAcimaDoLimiteException;
import br.com.fiapstore.pedido.domain.usecase.ConfirmarPedidoUseCase;
import br.com.fiapstore.pedido.infra.messaging.entity.MensagemFila;
import com.google.gson.Gson;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SagaPedido extends RouteBuilder {

    @Autowired
    private Gson gson;

    @Autowired
    private ConfirmarPedidoUseCase confirmarPedidoUseCase;

    @Override
    public void configure() throws Exception {
        from("spring-rabbitmq:demo?queues=orquestracao_pedidos&routingKey=orquestracao_pedidos&arg.queue.durable=true")
                .log(LoggingLevel.WARN, "Novo Pedido Recebido")
                .bean(MensagemFila.class, "fromJsonToMap(${body})")
                .convertBodyTo(String.class)
                .to("spring-rabbitmq:demo?queues=orquestracao_pagamentos&routingKey=orquestracao_pagamentos&arg.queue.durable=true");


        from("spring-rabbitmq:demo?queues=orquestracao_pedidos_saga_reply&routingKey=orquestracao_pedidos_saga_reply&arg.queue.durable=true")
                .routeId("Rota orquestracao_pedidos_saga_reply")
                .log(LoggingLevel.WARN, "${body}")
                .choice()
                    .when(body().contains("atualizacaoPagamento"))
                       .to("spring-rabbitmq:demo?queues=orquestracao_entregas&routingKey=orquestracao_entregas&arg.queue.durable=true")
                    .when(body().contains("\"statusEntrega\":\"CONFIRMADA\""))
                            .log(LoggingLevel.WARN, "entrega confirmada")
                            .bean(SagaPedido.class,"confirmarPedido(${body})")
                            .log(LoggingLevel.WARN, "Pedido confirmado");

    }

    public void confirmarPedido(String json) throws PedidoNaoEncontradoException, PercentualDescontoAcimaDoLimiteException, OperacaoInvalidaException {
        HashMap<String, String> map = MensagemFila.fromJsonToMap(json);
        confirmarPedidoUseCase.executar(map.get("codigoPedido"));

    }



}
