package br.com.fiapstore.cobranca.infra.database;

import br.com.fiapstore.cobranca.domain.entity.Pagamento;
import br.com.fiapstore.cobranca.domain.repository.IPagamentoDatabaseAdapter;
import br.com.fiapstore.cobranca.infra.database.entity.PagamentoEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class PagamentoDatabaseAdapter implements IPagamentoDatabaseAdapter {

    private final IPagamentoRepository pagamentoRepository;

    public PagamentoDatabaseAdapter(IPagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    @Override
    public Pagamento save(Pagamento pagamento) {
        PagamentoEntity pagamentoEntity = pagamentoRepository.save(toPagamentoEntity(pagamento));
        return toPagamento(pagamentoEntity);
    }

    @Override
    public Pagamento findByCodigo(String codigoPagamento) {
       PagamentoEntity pagamentoEntity = pagamentoRepository.findPagamentoEntityByCodigo(codigoPagamento);
        return toPagamento(pagamentoEntity);
    }

    public Pagamento findByCodigoPedido(String codigoPedido) {
        PagamentoEntity pagamentoEntity = pagamentoRepository.findPagamentoEntityByCodigoPedido(codigoPedido);
        return toPagamento(pagamentoEntity);
    }

    private static Pagamento toPagamento(PagamentoEntity pagamentoEntity) {
        Pagamento pagamento = null;

        if(pagamentoEntity!=null)
            pagamento =  new Pagamento(
                    pagamentoEntity.getId(),
                    UUID.fromString(pagamentoEntity.getCodigo()),
                    pagamentoEntity.getCodigoPedido(),
                    pagamentoEntity.getValor(),
                    pagamentoEntity.getPercentualDesconto(),
                    pagamentoEntity.getCpf(),
                    pagamentoEntity.getDataVencimento(),
                    pagamentoEntity.getStatusPagamento());
        return pagamento;
    }

    private static PagamentoEntity toPagamentoEntity(Pagamento pagamento) {
        PagamentoEntity pagamentoEntity = null;

        if(pagamento!=null)
            pagamentoEntity =  new PagamentoEntity(
                                pagamento.getId(),
                                pagamento.getCodigo().toString(),
                                pagamento.getCodigoPedido(),
                                pagamento.getValor(),
                                pagamento.getPercentualDesconto(),
                                pagamento.getCpf(),
                                pagamento.getDataVencimento(),
                                pagamento.getStatusPagamento());
        return pagamentoEntity;
    }
}
