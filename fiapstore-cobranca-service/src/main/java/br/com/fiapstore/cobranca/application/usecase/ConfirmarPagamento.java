package br.com.fiapstore.cobranca.application.usecase;

import br.com.fiapstore.cobranca.application.dto.PagamentoDto;
import br.com.fiapstore.cobranca.domain.entity.Pagamento;
import br.com.fiapstore.cobranca.domain.exception.OperacaoInvalidaException;
import br.com.fiapstore.cobranca.domain.exception.PagamentoNaoEncontradoException;
import br.com.fiapstore.cobranca.domain.repository.IPagamentoDatabaseAdapter;
import br.com.fiapstore.cobranca.domain.usecase.IConfirmarPagamentoUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfirmarPagamento implements IConfirmarPagamentoUseCase {

    private final IPagamentoDatabaseAdapter iPagamentoDatabaseAdapter;

    public ConfirmarPagamento(IPagamentoDatabaseAdapter iPagamentoDatabaseAdapter) {
        this.iPagamentoDatabaseAdapter = iPagamentoDatabaseAdapter;

    }


    public PagamentoDto executar(String codigoPagamento) throws PagamentoNaoEncontradoException, OperacaoInvalidaException {
        Pagamento pagamento = null;

        pagamento = this.iPagamentoDatabaseAdapter.findByCodigo(codigoPagamento);

        if(pagamento==null) throw new PagamentoNaoEncontradoException("Pagamento não encontrado");

        pagamento.confirmar();

        pagamento = this.iPagamentoDatabaseAdapter.save(pagamento);

        return PagamentoDto.toPagamentoDto(pagamento);
    }
}
