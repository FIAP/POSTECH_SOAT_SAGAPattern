package br.com.fiapstore.pedido.domain.entity;

import br.com.fiapstore.pedido.domain.exception.OperacaoInvalidaException;
import br.com.fiapstore.pedido.domain.exception.QuantidadeInvalidaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Pedido implements Serializable {

    public Pedido(String cpf,Produto produto,Long quantidadeProduto) {
        this.codigoPedido=UUID.randomUUID();
        this.cpf = cpf;
        this.statusPedido = StatusPedido.CRIADO;
        this.produto = produto;
        this.quantidadeProduto = quantidadeProduto;
    }
    private long id;
    private UUID codigoPedido;
    private StatusPedido statusPedido;
    private final String cpf;
    private CupomDesconto cupomDesconto;
    private Produto produto;
    private Long quantidadeProduto;

    public void aplicarDesconto(CupomDesconto codigoCupom) {
        this.cupomDesconto = codigoCupom;
    }
    public void cancelarPedido() throws OperacaoInvalidaException {
        if(!this.statusPedido.equals(StatusPedido.CRIADO)){
            throw new OperacaoInvalidaException("Operacao Inválida. O pedido encontra-se com status: "+this.statusPedido.toString());
        }
        this.statusPedido = StatusPedido.CANCELADO;
    }
    public void confirmarPedido() throws OperacaoInvalidaException {
        if(!this.statusPedido.equals(StatusPedido.CRIADO)){
            throw new OperacaoInvalidaException("Operacao Inválida. O pedido encontra-se com status: "+this.statusPedido.toString());
        }
        this.statusPedido = StatusPedido.CONFIRMADO;
    }

    public void addProduto(Produto produto){
        this.produto = produto;
    }

    public void atualizarQuantidadeProduto(Long quantidade) throws QuantidadeInvalidaException {
        if(quantidade <=0)
            throw new QuantidadeInvalidaException("Quantidade não pode ser menor ou igual a zero");

        this.quantidadeProduto = quantidade;

    }
    public Double calcularPrecoTotal(){
        return this.produto.getPreco()*this.quantidadeProduto;
    }

    public Double calcularPrecoTotalComDesconto(){
        if(this.cupomDesconto==null)
            return this.calcularPrecoTotal();

        double subtotal = this.calcularPrecoTotal();
        return subtotal-(subtotal*this.cupomDesconto.getPercentual()/100);
    }
}
