package br.com.fiapstore.cobranca.domain.exception;

public class PagamentoNaoEncontradoException extends Exception{
    public PagamentoNaoEncontradoException(String message) {
        super(message);
    }
}
