package br.com.fiapstore.pedido.infra.database;

import br.com.fiapstore.pedido.infra.database.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
    
}
