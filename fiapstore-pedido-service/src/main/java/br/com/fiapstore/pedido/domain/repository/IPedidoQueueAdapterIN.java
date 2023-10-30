package br.com.fiapstore.pedido.domain.repository;


import br.com.fiapstore.pedido.domain.exception.OperacaoInvalidaException;
import br.com.fiapstore.pedido.domain.exception.PedidoNaoEncontradoException;
import br.com.fiapstore.pedido.domain.exception.PercentualDescontoAcimaDoLimiteException;
import org.springframework.messaging.handler.annotation.Payload;

public interface IPedidoQueueAdapterIN {

    void receive(@Payload String message) throws PedidoNaoEncontradoException,
            PercentualDescontoAcimaDoLimiteException,
            OperacaoInvalidaException;

}
