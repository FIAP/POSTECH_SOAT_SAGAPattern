package br.com.fiapstore.pedido.domain.usecase;

import br.com.fiapstore.pedido.domain.exception.OperacaoInvalidaException;
import br.com.fiapstore.pedido.domain.exception.PercentualDescontoAcimaDoLimiteException;
import br.com.fiapstore.pedido.domain.exception.PedidoNaoEncontradoException;

public interface ConfirmarPedidoUseCase {

    void executar(String codigoPedido) throws PercentualDescontoAcimaDoLimiteException, PedidoNaoEncontradoException, OperacaoInvalidaException;
}
