package br.com.fiapstore.pedido.domain.usecase;

import br.com.fiapstore.pedido.application.dto.PedidoDto;
import br.com.fiapstore.pedido.domain.exception.CupomExpiradoException;
import br.com.fiapstore.pedido.domain.exception.CupomInvalidoException;
import br.com.fiapstore.pedido.domain.exception.PercentualDescontoAcimaDoLimiteException;
import br.com.fiapstore.pedido.domain.exception.ProdutoNaoEncontradoException;

public interface RegistrarPedidoUseCase {

    PedidoDto executar(PedidoDto pedidoDto) throws PercentualDescontoAcimaDoLimiteException, CupomExpiradoException, CupomInvalidoException, ProdutoNaoEncontradoException;
}
