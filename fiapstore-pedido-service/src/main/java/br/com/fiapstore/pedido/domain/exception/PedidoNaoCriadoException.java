package br.com.fiapstore.pedido.domain.exception;

public class PedidoNaoCriadoException extends Exception{
    public PedidoNaoCriadoException(String message) {
        super(message);
    }
}
