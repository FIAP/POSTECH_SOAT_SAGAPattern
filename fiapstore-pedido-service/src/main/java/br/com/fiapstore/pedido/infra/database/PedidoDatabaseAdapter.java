package br.com.fiapstore.pedido.infra.database;

import br.com.fiapstore.pedido.domain.entity.CupomDesconto;
import br.com.fiapstore.pedido.domain.entity.Pedido;
import br.com.fiapstore.pedido.domain.entity.Produto;
import br.com.fiapstore.pedido.domain.repository.IPedidoDatabaseAdapter;
import br.com.fiapstore.pedido.infra.database.entity.CupomDescontoEntity;
import br.com.fiapstore.pedido.infra.database.entity.PedidoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoDatabaseAdapter implements IPedidoDatabaseAdapter {

    @Autowired
    private final PedidoRepository pedidoRepository;

    @Override
    public Pedido save(Pedido pedido) {
        PedidoEntity pedidoEntity = toPedidoEntity(pedido);
        PedidoEntity entity = pedidoRepository.save(pedidoEntity);
        return toPedido(entity);
    }

    @Override
    public Pedido findByCodigoPedido(String codigo) {
        PedidoEntity pedidoEntity = pedidoRepository.findPedidoEntityByCodigoPedido(codigo);
        return toPedido(pedidoEntity);
    }

    @Override
    public List<Pedido> findAll() {
        List<Pedido> pedidos = new ArrayList<>();
        pedidoRepository.findAll().forEach(pedidoEntity -> pedidos.add(toPedido(pedidoEntity)));
        return pedidos;
    }

    private static Pedido toPedido(PedidoEntity pedidoEntity) {
        Pedido pedido = null;
        if (pedidoEntity != null && pedidoEntity.getCupomDescontoEntity() != null) {
            CupomDesconto cupomDesconto = new CupomDesconto(
                    pedidoEntity.getCupomDescontoEntity().getCodigo(),
                    pedidoEntity.getCupomDescontoEntity().getPercentual(),
                    pedidoEntity.getCupomDescontoEntity().getDataUso(),
                    pedidoEntity.getCupomDescontoEntity().getDataVencimento());

            Produto produto = ProdutoDatabaseAdapter.toProduto(pedidoEntity.getProdutoEntity());

            pedido = new Pedido(pedidoEntity.getId(),UUID.fromString(pedidoEntity.getCodigoPedido()), pedidoEntity.getStatusPedido(), pedidoEntity.getCpf(), cupomDesconto, produto, pedidoEntity.getQuantidade());
        }
        return pedido;
    }

    private static PedidoEntity toPedidoEntity(Pedido pedido) {

        CupomDescontoEntity cupomDescontoEntity = null;
        PedidoEntity pedidoEntity = null;

        if(pedido!=null && pedido.getCupomDesconto()!=null) {
            cupomDescontoEntity = new CupomDescontoEntity(
                    pedido.getCupomDesconto().getCodigo(),
                    pedido.getCupomDesconto().getDataUso(),
                    pedido.getCupomDesconto().getPercentual(),
                    pedido.getCupomDesconto().getDataVencimento());

            pedidoEntity = new PedidoEntity(
                    pedido.getId(),
                    pedido.getCodigoPedido().toString(),
                    pedido.getCpf(),
                    pedido.getStatusPedido(),
                    cupomDescontoEntity,
                    ProdutoDatabaseAdapter.toProdutoEntity(pedido.getProduto()),
                    pedido.getQuantidadeProduto());
        }
        return pedidoEntity;
    }
}
