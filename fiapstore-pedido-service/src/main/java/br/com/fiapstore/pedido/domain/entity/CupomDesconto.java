package br.com.fiapstore.pedido.domain.entity;

import br.com.fiapstore.pedido.domain.exception.CupomExpiradoException;
import br.com.fiapstore.pedido.domain.exception.CupomInvalidoException;
import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
@Getter
public class CupomDesconto {

    private final String codigo;
    private final LocalDate dataUso;
    private Double percentual;
    private LocalDate dataVencimento;

    public CupomDesconto(String codigo) throws CupomExpiradoException, CupomInvalidoException {
        this.codigo = codigo;
        this.dataUso=LocalDate.now();
        recuperarDadosCupom();
        checarCupom();
    }

    public CupomDesconto(String codigo, Double percentual, LocalDate dataUso, LocalDate dataVencimento) {
        this.codigo = codigo;
        this.percentual = percentual;
        this.dataUso = dataUso;
        this.dataVencimento = dataVencimento;
    }

    private void recuperarDadosCupom(){
        switch (this.codigo) {
            case "TESTE100" -> {
                this.percentual = 10.0;
                this.dataVencimento = LocalDate.now().plus(1, ChronoUnit.DAYS);
            }
            case "TESTE200" -> {
                this.percentual = 20.0;
                this.dataVencimento = LocalDate.now().minus(1, ChronoUnit.DAYS);
            }
            case "TESTE300" -> {
                this.percentual = 5.0;
                this.dataVencimento = LocalDate.now().minus(10, ChronoUnit.DAYS);
            }
            default -> {
                this.percentual = 0.0;
                this.dataVencimento = LocalDate.now();
            }
        }
    }
    public void checarCupom() throws CupomExpiradoException, CupomInvalidoException {
        if(this.dataVencimento.isBefore(this.dataUso)){
            throw new CupomExpiradoException("Cupom Expirado");
        }

        if(this.percentual == null){
            throw new CupomInvalidoException("Cupom Inválido");
        }
    }
}
