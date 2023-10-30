package br.com.fiapstore.entrega.domain.repository;


import br.com.fiapstore.entrega.domain.entity.Entrega;

public interface IEntregaQueueAdapterOUT {

    void publishEntregaConfirmada(Entrega entrega);

}
