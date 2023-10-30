package br.com.fiapstore.pedido.infra.messaging;

import br.com.fiapstore.pedido.domain.repository.IPedidoQueueAdapterOUT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PedidoQueueAdapterOUT implements IPedidoQueueAdapterOUT {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${queue1.name}")
    private String pedidosPendentes;

    @Override
    public void publish(String message) {
        rabbitTemplate.convertAndSend(pedidosPendentes, message);
        logger.info("Publicação na fila pedidos executada");

    }
}
