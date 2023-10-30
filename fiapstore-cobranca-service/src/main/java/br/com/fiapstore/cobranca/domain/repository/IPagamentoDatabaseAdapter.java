package br.com.fiapstore.cobranca.domain.repository;

import br.com.fiapstore.cobranca.domain.entity.Pagamento;

public interface IPagamentoDatabaseAdapter {
    Pagamento save(Pagamento pagamento);

    Pagamento findByCodigo(String codigoPagamento);

}
