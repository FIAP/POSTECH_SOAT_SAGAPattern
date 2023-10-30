package br.com.fiapstore.cobranca.application.usecase;

import br.com.fiapstore.cobranca.application.dto.PagamentoDto;
import br.com.fiapstore.cobranca.domain.entity.Pagamento;
import br.com.fiapstore.cobranca.domain.exception.OperacaoInvalidaException;
import br.com.fiapstore.cobranca.domain.exception.PagamentoNaoEncontradoException;
import br.com.fiapstore.cobranca.domain.repository.IPagamentoDatabaseAdapter;
import br.com.fiapstore.cobranca.domain.repository.IPagamentoQueueAdapter;
import br.com.fiapstore.cobranca.domain.usecase.IConfirmarPagamentoUseCase;
import br.com.fiapstore.cobranca.infra.messaging.PagamentoQueueAdapterOUT;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfirmarPagamento implements IConfirmarPagamentoUseCase {

    private final IPagamentoDatabaseAdapter iPagamentoDatabaseAdapter;
    private final IPagamentoQueueAdapter pagamentoQueueAdapter;

    public ConfirmarPagamento(IPagamentoDatabaseAdapter iPagamentoDatabaseAdapter, IPagamentoQueueAdapter iPagamentoQueueAdapter) {
        this.iPagamentoDatabaseAdapter = iPagamentoDatabaseAdapter;
        this.pagamentoQueueAdapter = iPagamentoQueueAdapter;
    }


    @Transactional
    public PagamentoDto executar(String codigoPagamento) throws PagamentoNaoEncontradoException, OperacaoInvalidaException {
        Pagamento pagamento = null;

        pagamento = this.iPagamentoDatabaseAdapter.findByCodigo(codigoPagamento);

        if(pagamento==null) throw new PagamentoNaoEncontradoException("Pagamento não encontrado");

        pagamento.confirmar();

        pagamento = this.iPagamentoDatabaseAdapter.save(pagamento);

        this.pagamentoQueueAdapter.publishAtualizacaoPagamento(PagamentoQueueAdapterOUT.toMessage(pagamento));

        return PagamentoDto.toPagamentoDto(pagamento);
    }
}
