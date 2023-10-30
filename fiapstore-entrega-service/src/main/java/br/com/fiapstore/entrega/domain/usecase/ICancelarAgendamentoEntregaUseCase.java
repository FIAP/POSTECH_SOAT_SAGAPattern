package br.com.fiapstore.entrega.domain.usecase;

import br.com.fiapstore.entrega.application.dto.EntregaDto;
import br.com.fiapstore.entrega.domain.exception.EntregaNaoEncontradaException;
import br.com.fiapstore.entrega.domain.exception.OperacaoInvalidaException;

public interface ICancelarAgendamentoEntregaUseCase {

    EntregaDto executar(String codigo) throws OperacaoInvalidaException, EntregaNaoEncontradaException;

}
