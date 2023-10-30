package br.com.fiapstore.cobranca.domain.usecase;

import br.com.fiapstore.cobranca.application.dto.PagamentoDto;

public interface IRegistrarPagamentoUseCase {

    PagamentoDto executar(PagamentoDto pagamentoDto);

}
