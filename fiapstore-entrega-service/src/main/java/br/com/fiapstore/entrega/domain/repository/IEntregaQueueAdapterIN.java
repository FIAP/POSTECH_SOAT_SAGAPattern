package br.com.fiapstore.entrega.domain.repository;

import br.com.fiapstore.entrega.application.dto.EntregaDto;
import br.com.fiapstore.entrega.domain.exception.EntregaNaoEncontradaException;
import br.com.fiapstore.entrega.domain.exception.OperacaoInvalidaException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

public interface IEntregaQueueAdapterIN {
    static EntregaDto fromMessageToDto(Map mensagem) {
        return new EntregaDto(
                null,
                (String) mensagem.get("codigoPedido"),
                (String) mensagem.get("cpf"),
                null,
                null,
                null
        );
    }

    void receive(@Payload String message) throws EntregaNaoEncontradaException, OperacaoInvalidaException;
}
