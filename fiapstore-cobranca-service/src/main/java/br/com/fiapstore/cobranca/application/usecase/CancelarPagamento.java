package br.com.fiapstore.cobranca.application.usecase;

import br.com.fiapstore.cobranca.application.dto.PagamentoDto;
import br.com.fiapstore.cobranca.domain.entity.Pagamento;
import br.com.fiapstore.cobranca.domain.exception.OperacaoInvalidaException;
import br.com.fiapstore.cobranca.domain.exception.PagamentoNaoEncontradoException;
import br.com.fiapstore.cobranca.domain.repository.IPagamentoDatabaseAdapter;
import br.com.fiapstore.cobranca.domain.usecase.ICancelarPagamentoUseCase;
import org.springframework.stereotype.Service;

@Service
public class CancelarPagamento implements ICancelarPagamentoUseCase {

    private final IPagamentoDatabaseAdapter iPagamentoDatabaseAdapter;

    public CancelarPagamento(IPagamentoDatabaseAdapter iPagamentoDatabaseAdapter) {
        this.iPagamentoDatabaseAdapter = iPagamentoDatabaseAdapter;
    }


    @Override
    public PagamentoDto executar(String codigoPagamento) throws PagamentoNaoEncontradoException, OperacaoInvalidaException {
        Pagamento pagamento = null;

        pagamento = this.iPagamentoDatabaseAdapter.findByCodigo(codigoPagamento);

        if(pagamento==null) throw new PagamentoNaoEncontradoException("Pagamento não encontrado");

        pagamento.cancelar();

        pagamento = this.iPagamentoDatabaseAdapter.save(pagamento);

        return PagamentoDto.toPagamentoDto(pagamento);
    }
}
