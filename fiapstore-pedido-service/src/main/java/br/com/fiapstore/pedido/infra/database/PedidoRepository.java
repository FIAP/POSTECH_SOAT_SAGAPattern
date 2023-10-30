package br.com.fiapstore.pedido.infra.database;

import br.com.fiapstore.pedido.infra.database.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {
    PedidoEntity findPedidoEntityByCodigoPedido(String codigoPedido);
}
