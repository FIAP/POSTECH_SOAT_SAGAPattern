package br.com.fiapstore.cobranca.infra.messaging;

import br.com.fiapstore.cobranca.domain.entity.Pagamento;
import br.com.fiapstore.cobranca.domain.repository.IPagamentoQueueAdapter;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PagamentoQueueAdapterOUT implements IPagamentoQueueAdapter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${queue1.name}")
    private String filaReplySagaPedido;


    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public void publishAtualizacaoPagamento(String message) {
        rabbitTemplate.convertAndSend(filaReplySagaPedido, message);
        logger.info("Publicação na fila filaSagaPedidoReply executada");
    }


    public static String toMessage(Pagamento pagamento){
        Map message = new HashMap<String, String>();
        message.put("tipoOperacao","atualizacaoPagamento");
        message.put("codigoPagamento",pagamento.getCodigo());
        message.put("codigoPedido",pagamento.getCodigoPedido());
        message.put("cpf",pagamento.getCpf());
        message.put("statusPagamento",pagamento.getStatusPagamento());
        return new Gson().toJson(message);
    }

}
