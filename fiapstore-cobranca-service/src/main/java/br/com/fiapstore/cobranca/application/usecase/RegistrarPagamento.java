package br.com.fiapstore.cobranca.application.usecase;

import br.com.fiapstore.cobranca.application.dto.PagamentoDto;
import br.com.fiapstore.cobranca.domain.entity.Pagamento;
import br.com.fiapstore.cobranca.domain.repository.IPagamentoDatabaseAdapter;
import br.com.fiapstore.cobranca.domain.usecase.IRegistrarPagamentoUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrarPagamento implements IRegistrarPagamentoUseCase {


    private final IPagamentoDatabaseAdapter iPagamentoDatabaseAdapter;
    @Autowired
    public RegistrarPagamento(IPagamentoDatabaseAdapter iPagamentoDatabaseAdapter){
        this.iPagamentoDatabaseAdapter = iPagamentoDatabaseAdapter;

    }
    public PagamentoDto executar(PagamentoDto pagamentoDto) {

        Pagamento pagamento = new Pagamento(pagamentoDto.getCodigoPedido(), pagamentoDto.getValor(), pagamentoDto.getPercentualDesconto(), pagamentoDto.getCpf());
        pagamento = iPagamentoDatabaseAdapter.save(pagamento);
        return PagamentoDto.toPagamentoDto(pagamento);
    }


}
