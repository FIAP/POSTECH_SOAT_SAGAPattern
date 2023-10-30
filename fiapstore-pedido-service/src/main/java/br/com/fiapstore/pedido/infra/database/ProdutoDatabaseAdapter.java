package br.com.fiapstore.pedido.infra.database;

import br.com.fiapstore.pedido.domain.entity.Produto;
import br.com.fiapstore.pedido.domain.repository.IProdutoDatabaseAdapter;
import br.com.fiapstore.pedido.infra.database.entity.ProdutoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProdutoDatabaseAdapter implements IProdutoDatabaseAdapter {

    @Autowired
    private final ProdutoRepository produtoRepository;

    @Override
    public Produto findById(Long id) {
        Optional<ProdutoEntity> optional = produtoRepository.findById(id);
        if(optional.isPresent())
            return toProduto(optional.get());
        return null;
    }

    public static Produto toProduto(ProdutoEntity produtoEntity){
        return new Produto(produtoEntity.getId(), produtoEntity.getNome(), produtoEntity.getPreco());
    }

    public static ProdutoEntity toProdutoEntity(Produto produto){
        return new ProdutoEntity(produto.getId(), produto.getNome(), produto.getPreco());
    }
}
