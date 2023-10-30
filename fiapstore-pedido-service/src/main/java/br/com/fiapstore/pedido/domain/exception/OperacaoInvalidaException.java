package br.com.fiapstore.pedido.domain.exception;

public class OperacaoInvalidaException extends Exception{
    public OperacaoInvalidaException(String message) {
        super(message);
    }
}
