package br.com.fiapstore.pedido.application.usecase;


import br.com.fiapstore.pedido.domain.entity.Pedido;
import br.com.fiapstore.pedido.domain.exception.OperacaoInvalidaException;
import br.com.fiapstore.pedido.domain.exception.PedidoNaoEncontradoException;
import br.com.fiapstore.pedido.domain.repository.IPedidoDatabaseAdapter;
import br.com.fiapstore.pedido.domain.usecase.CancelarPedidoUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CancelarPedido implements CancelarPedidoUseCase {

    private final IPedidoDatabaseAdapter pedidoDatabaseAdapter;

    public CancelarPedido(IPedidoDatabaseAdapter pedidoDatabaseAdapter) {
        this.pedidoDatabaseAdapter = pedidoDatabaseAdapter;

    }
    @Transactional
    public void executar(String codigoPedido) throws PedidoNaoEncontradoException, OperacaoInvalidaException {

        Pedido pedido = pedidoDatabaseAdapter.findByCodigoPedido(codigoPedido);

        if(pedido==null) throw new PedidoNaoEncontradoException("Produto não encontrado");
        pedido.cancelarPedido();
        pedidoDatabaseAdapter.save(pedido);

    }
}
