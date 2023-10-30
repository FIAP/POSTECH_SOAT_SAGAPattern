package br.com.fiapstore.pedido.infra.database.entity;

import br.com.fiapstore.pedido.domain.entity.StatusPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String codigoPedido;

    private String cpf;

    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

    @OneToOne(cascade = CascadeType.ALL)
    private CupomDescontoEntity cupomDescontoEntity;

    @ManyToOne
    private ProdutoEntity produtoEntity;

    private Long quantidade;

}
