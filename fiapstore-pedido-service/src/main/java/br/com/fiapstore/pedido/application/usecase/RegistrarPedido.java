package br.com.fiapstore.pedido.application.usecase;


import br.com.fiapstore.pedido.application.dto.PedidoDto;
import br.com.fiapstore.pedido.domain.entity.CupomDesconto;
import br.com.fiapstore.pedido.domain.entity.Pedido;
import br.com.fiapstore.pedido.domain.entity.Produto;
import br.com.fiapstore.pedido.domain.exception.CupomExpiradoException;
import br.com.fiapstore.pedido.domain.exception.CupomInvalidoException;
import br.com.fiapstore.pedido.domain.exception.ProdutoNaoEncontradoException;
import br.com.fiapstore.pedido.domain.repository.IPedidoDatabaseAdapter;
import br.com.fiapstore.pedido.domain.repository.IProdutoDatabaseAdapter;
import br.com.fiapstore.pedido.domain.usecase.RegistrarPedidoUseCase;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class RegistrarPedido implements RegistrarPedidoUseCase {

    private final IPedidoDatabaseAdapter pedidoDatabaseAdapter;
    private final IProdutoDatabaseAdapter produtoDatabaseAdapter;

    @Autowired
    private Gson gson;

    public RegistrarPedido(IPedidoDatabaseAdapter pedidoDatabaseAdapter, IProdutoDatabaseAdapter produtoDatabaseAdapter) {
        this.pedidoDatabaseAdapter = pedidoDatabaseAdapter;
         this.produtoDatabaseAdapter = produtoDatabaseAdapter;
    }


    public PedidoDto executar(PedidoDto pedidoDto) throws CupomExpiradoException, CupomInvalidoException, ProdutoNaoEncontradoException {

        Produto produto = produtoDatabaseAdapter.findById(pedidoDto.getCodigoProduto());
        if(produto==null)
            throw new ProdutoNaoEncontradoException("Produto não Encontrado");

        Pedido pedido = new Pedido(pedidoDto.getCpf(),produto,pedidoDto.getQuantidadeProduto());

        CupomDesconto cupomDesconto = new CupomDesconto(pedidoDto.getCodigoCupom());
        pedido.aplicarDesconto(cupomDesconto);

        Pedido pedidoSalvo  =pedidoDatabaseAdapter.save(pedido);

        return toPedidoDto(pedidoSalvo);
    }

    private PedidoDto toPedidoDto(Pedido pedido){
        return new PedidoDto(
                pedido.getCodigoPedido().toString(),
                pedido.getCpf(),
                pedido.getCupomDesconto().getCodigo(),
                pedido.getStatusPedido(),
                pedido.getProduto().getId(),
                pedido.getQuantidadeProduto(),
                pedido.calcularPrecoTotal(),
                pedido.calcularPrecoTotalComDesconto(),
                pedido.getCupomDesconto().getPercentual()
        );

    }

    private String toPedidoMessage(Pedido pedido){
        Map message = new HashMap<String, String>();
        message.put("codigoPedido",pedido.getCodigoPedido());
        message.put("precoTotal",pedido.calcularPrecoTotal());
        message.put("percentualDesconto",pedido.getCupomDesconto().getPercentual());
        message.put("cpf",pedido.getCpf());
        return gson.toJson(message);
    }
}
