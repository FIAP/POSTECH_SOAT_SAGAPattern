package br.com.fiapstore.entrega.infra.messaging;

import br.com.fiapstore.entrega.application.dto.EntregaDto;
import br.com.fiapstore.entrega.application.usecase.RegistrarAgendamentoEntrega;
import br.com.fiapstore.entrega.domain.repository.IEntregaQueueAdapterIN;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class EntregaPagamentoPendenteQueueAdapterIN implements IEntregaQueueAdapterIN {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Gson gson;

    private final RegistrarAgendamentoEntrega registrarAgendamentoEntrega;

    @Autowired
    public EntregaPagamentoPendenteQueueAdapterIN(RegistrarAgendamentoEntrega registrarAgendamentoEntrega){
        this.registrarAgendamentoEntrega = registrarAgendamentoEntrega;
    }

    @RabbitListener(queues = {"${queue2.name}"})
    public void receive(@Payload String message) {

        HashMap<String, String> mensagem = gson.fromJson(message, HashMap.class);
        EntregaDto entregaDto = IEntregaQueueAdapterIN.fromMessageToDto(mensagem);

        registrarAgendamentoEntrega.executar(entregaDto);
        logger.info("Pendencia de entrega registrada",entregaDto);
    }

}
