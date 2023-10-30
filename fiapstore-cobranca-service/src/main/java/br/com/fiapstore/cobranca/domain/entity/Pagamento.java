package br.com.fiapstore.cobranca.domain.entity;

import br.com.fiapstore.cobranca.domain.exception.OperacaoInvalidaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Pagamento {

    private static final Double TAXA_ENTREGA=15.0;
    private static final int PRAZO_ENTREGA_PADRAO = 5;

    private long id;
    private UUID codigo;
    private String codigoPedido;
    private Double valor;
    private Double percentualDesconto;
    private String cpf;
    private LocalDate dataVencimento;
    private StatusPagamento statusPagamento;

    public Pagamento(String codigoPedido,Double valor,Double percentualDesconto,String cpf){
        this.valor = valor;
        this.percentualDesconto=percentualDesconto;
        this.codigoPedido = codigoPedido;
        this.cpf = cpf;
        configurarPagamento();
    }

    private void configurarPagamento(){
        this.codigo = UUID.randomUUID();
        this.statusPagamento=StatusPagamento.PENDENTE;
        calcularVencimento();
        calcularValor();
    }

    private void calcularVencimento(){
        this.dataVencimento = LocalDate.now().plus(PRAZO_ENTREGA_PADRAO, ChronoUnit.DAYS);
    }

    private void calcularValor(){
        this.valor -= this.valor*this.percentualDesconto/100;
        this.valor += TAXA_ENTREGA;
    }
    public void cancelar() throws OperacaoInvalidaException {
        if(this.statusPagamento.equals(StatusPagamento.REALIZADO)){
            throw new OperacaoInvalidaException("Operacao Inválida. O pagamento encontra-se com status: "+this.statusPagamento.toString());
        }
        this.statusPagamento = StatusPagamento.CANCELADO;
    }

    public void confirmar() throws OperacaoInvalidaException {
        if(this.statusPagamento.equals(StatusPagamento.CANCELADO)){
            throw new OperacaoInvalidaException("Operacao Inválida. O pagamento encontra-se com status: "+this.statusPagamento.toString());
        }
        this.statusPagamento = StatusPagamento.REALIZADO;
    }
}
