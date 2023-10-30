package br.com.fiapstore.cobranca.domain.repository;


public interface IPagamentoQueueAdapter {
    void publishAtualizacaoPagamento(String pagamentoJson);


}
