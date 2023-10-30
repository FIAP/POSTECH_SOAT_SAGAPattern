package br.com.fiapstore.pedido.domain.exception;

public class CupomExpiradoException extends Exception{
    public CupomExpiradoException(String message) {
        super(message);
    }
}
