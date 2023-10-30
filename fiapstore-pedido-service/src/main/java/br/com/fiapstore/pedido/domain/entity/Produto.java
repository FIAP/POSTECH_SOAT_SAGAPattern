package br.com.fiapstore.pedido.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Produto {

    private Long id;
    private String nome;
    private Double preco;
}
