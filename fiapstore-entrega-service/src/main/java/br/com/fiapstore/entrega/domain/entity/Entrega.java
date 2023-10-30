package br.com.fiapstore.entrega.domain.entity;

import br.com.fiapstore.entrega.domain.exception.OperacaoInvalidaException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Entrega {


    private static final long PRAZO_ENTREGA_PADRAO = 3;
    private static final String MEIO_ENTREGA_PADRAO = "SEDEX";

    private long id;
    private UUID codigo;
    private String codigoPedido;
    private String cpf;
    private LocalDate dataPrevistaEntrega;
    private String meioEntrega;
    private StatusEntrega statusEntrega;

    public Entrega(String codigoPedido, String cpf) {
        this.codigoPedido = codigoPedido;
        this.cpf = cpf;
        configurarEntrega();
    }


    private void configurarEntrega(){
        this.codigo = UUID.randomUUID();
        calcularDataEntrega();
        this.meioEntrega = MEIO_ENTREGA_PADRAO;
        this.statusEntrega = StatusEntrega.PENDENTE;

    }

    private void calcularDataEntrega(){
        this.dataPrevistaEntrega = LocalDate.now().plus(PRAZO_ENTREGA_PADRAO, ChronoUnit.DAYS);

        if(this.dataPrevistaEntrega.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
            this.dataPrevistaEntrega = this.dataPrevistaEntrega.plus(2, ChronoUnit.DAYS);

        }else  if(this.dataPrevistaEntrega.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
            this.dataPrevistaEntrega = this.dataPrevistaEntrega.plus(1, ChronoUnit.DAYS);
        }
    }

    public void cancelarEntrega() throws OperacaoInvalidaException {
        if(this.statusEntrega == StatusEntrega.CONFIRMADA || this.statusEntrega == StatusEntrega.CANCELADA)
            throw new OperacaoInvalidaException("Operacao Inválida. A entrega encontra-se com status: "+this.statusEntrega.toString());

        this.statusEntrega = StatusEntrega.CANCELADA;
    }

    public void confirmarEntrega() throws OperacaoInvalidaException {
        if(this.statusEntrega == StatusEntrega.CONFIRMADA || this.statusEntrega == StatusEntrega.CANCELADA)
            throw new OperacaoInvalidaException("Operacao Inválida. A entrega encontra-se com status: "+this.statusEntrega.toString());

        this.statusEntrega = StatusEntrega.CONFIRMADA;
    }
}
