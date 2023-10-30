package br.com.fiapstore.pedido.infra.messaging.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MensagemFila implements Serializable {

    private TipoOperacao tipoOperacao;
    private String codigoPedido;
    private double precoTotal;
    private double percentualDesconto;
    private String codigoPagamento;
    private String codigoEntrega;
    private String statusPagamento;
    private String statusEntrega;
    private String statusPedido;
    private String cpf;

    public static HashMap<String, String> fromJsonToMap(String json){
        Type mapType = new TypeToken<HashMap<String, String>>(){}.getType();
        return new Gson().fromJson(json, mapType);
    }

    public static String toJson(HashMap<String, String> mensagemFila){
        return new Gson().toJson(mensagemFila);
    }
}
