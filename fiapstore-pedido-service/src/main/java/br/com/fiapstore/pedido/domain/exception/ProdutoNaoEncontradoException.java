package br.com.fiapstore.pedido.domain.exception;

public class ProdutoNaoEncontradoException extends Exception{
    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }
}
