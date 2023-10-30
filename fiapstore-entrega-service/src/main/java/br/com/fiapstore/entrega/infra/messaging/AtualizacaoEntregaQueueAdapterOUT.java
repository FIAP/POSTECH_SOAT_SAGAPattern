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
public class AtualizacaoEntregaQueueAdapterOUT implements IEntregaQueueAdapterOUT {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${queue1.name}")
    private String filaEntregas;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void publishEntregaConfirmada(Entrega entrega) {
        rabbitTemplate.convertAndSend(filaEntregas, toMessage(entrega));
        logger.info("Atualização entrega publicada");
    }

    public static String toMessage(Entrega entrega){
        Map message = new HashMap<String, String>();
        message.put("tipoOperacao","atualizacaoEntrega");
        message.put("codigoPedido",entrega.getCodigoPedido());
        message.put("codigoEntrega",entrega.getCodigo());
        message.put("cpf",entrega.getCpf());
        message.put("statusEntrega",entrega.getStatusEntrega());
        return new Gson().toJson(message);
    }

}
