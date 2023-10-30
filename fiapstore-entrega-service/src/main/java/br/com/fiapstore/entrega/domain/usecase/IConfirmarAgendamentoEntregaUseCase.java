package br.com.fiapstore.entrega.domain.usecase;

import br.com.fiapstore.entrega.application.dto.EntregaDto;
import br.com.fiapstore.entrega.domain.exception.EntregaNaoEncontradaException;
import br.com.fiapstore.entrega.domain.exception.OperacaoInvalidaException;

public interface IConfirmarAgendamentoEntregaUseCase {

    EntregaDto executar(String codigoEntrega, String codigoPedido) throws OperacaoInvalidaException, EntregaNaoEncontradaException;

}
