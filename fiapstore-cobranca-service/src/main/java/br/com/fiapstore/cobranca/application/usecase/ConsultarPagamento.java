package br.com.fiapstore.cobranca.application.usecase;

import br.com.fiapstore.cobranca.application.dto.PagamentoDto;
import br.com.fiapstore.cobranca.domain.entity.Pagamento;
import br.com.fiapstore.cobranca.domain.exception.PagamentoNaoEncontradoException;
import br.com.fiapstore.cobranca.domain.repository.IPagamentoDatabaseAdapter;
import br.com.fiapstore.cobranca.domain.usecase.IConsultarPagamentoUseCase;
import org.springframework.stereotype.Service;

@Service
public class ConsultarPagamento implements IConsultarPagamentoUseCase {

    private final IPagamentoDatabaseAdapter iPagamentoDatabaseAdapter;

    public ConsultarPagamento(IPagamentoDatabaseAdapter iPagamentoDatabaseAdapter) {
        this.iPagamentoDatabaseAdapter = iPagamentoDatabaseAdapter;
    }


    @Override
    public PagamentoDto executar(String codigo) throws PagamentoNaoEncontradoException {
        Pagamento pagamento;

        pagamento = iPagamentoDatabaseAdapter.findByCodigo(codigo);

        if(pagamento==null) throw new PagamentoNaoEncontradoException("Pagamento não encontrado");

        return PagamentoDto.toPagamentoDto(pagamento);
    }
}
