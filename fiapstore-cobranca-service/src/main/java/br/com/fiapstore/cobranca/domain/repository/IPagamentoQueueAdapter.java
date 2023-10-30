package br.com.fiapstore.cobranca.domain.repository;


public interface IPagamentoQueueAdapter {
    void publishPagamentoPendente(String pagamentoJson);

    void publishPagamentoConfirmado(String pagamentoJson);

}
