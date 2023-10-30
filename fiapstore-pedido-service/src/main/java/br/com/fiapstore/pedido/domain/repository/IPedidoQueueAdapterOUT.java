package br.com.fiapstore.pedido.domain.repository;


public interface IPedidoQueueAdapterOUT {
    void publish(String message);

}
