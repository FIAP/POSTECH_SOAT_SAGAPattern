package br.com.fiapstore.cobranca.infra.messaging;

import java.util.HashMap;

public interface IConverterMessage {

    public HashMap<String, String> translate(String message);
}
