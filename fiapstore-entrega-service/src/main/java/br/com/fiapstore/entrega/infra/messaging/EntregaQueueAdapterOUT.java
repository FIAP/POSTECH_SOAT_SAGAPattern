package br.com.fiapstore.entrega.infra.messaging;

import br.com.fiapstore.entrega.domain.entity.Entrega;
import br.com.fiapstore.entrega.domain.repository.IEntregaQueueAdapterOUT;
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
public class EntregaQueueAdapterOUT implements IEntregaQueueAdapterOUT {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${queue4.name}")
    private String filaEntregasConfirmadas;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void publishEntregaConfirmada(Entrega entrega) {
        rabbitTemplate.convertAndSend(filaEntregasConfirmadas, toMessage(entrega));
        logger.info("Publicação na fila entregasConfirmadas executada");
    }

    public static String toMessage(Entrega entrega){
        Map message = new HashMap<String, String>();
        message.put("codigoPedido",entrega.getCodigoPedido());
        message.put("codigoEntrega",entrega.getCodigo());
        message.put("cpf",entrega.getCpf());
        return new Gson().toJson(message);
    }

}
