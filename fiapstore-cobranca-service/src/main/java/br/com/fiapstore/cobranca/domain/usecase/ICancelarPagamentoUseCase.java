package br.com.fiapstore.cobranca.domain.usecase;

import br.com.fiapstore.cobranca.application.dto.PagamentoDto;
import br.com.fiapstore.cobranca.domain.exception.OperacaoInvalidaException;
import br.com.fiapstore.cobranca.domain.exception.PagamentoNaoEncontradoException;

public interface ICancelarPagamentoUseCase {

    PagamentoDto executar(String codigoPagamento) throws PagamentoNaoEncontradoException, OperacaoInvalidaException;

}
