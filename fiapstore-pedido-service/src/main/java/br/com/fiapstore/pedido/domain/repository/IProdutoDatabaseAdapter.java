package br.com.fiapstore.pedido.domain.repository;


import br.com.fiapstore.pedido.domain.entity.Produto;

public interface IProdutoDatabaseAdapter {

    Produto findById(Long id);

}
