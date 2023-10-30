package br.com.fiapstore.pedido.domain.repository;


import br.com.fiapstore.pedido.domain.entity.Pedido;
import br.com.fiapstore.pedido.infra.database.entity.PedidoEntity;

import java.util.List;

public interface IPedidoDatabaseAdapter {
    Pedido save(Pedido pedido);

    Pedido findByCodigoPedido(String codigo);
    List<Pedido> findAll();
}
