package br.com.fiapstore.entrega.infra.messaging;

import br.com.fiapstore.entrega.application.dto.EntregaDto;
import br.com.fiapstore.entrega.application.usecase.ConfirmarAgendamentoEntrega;
import br.com.fiapstore.entrega.application.usecase.RegistrarAgendamentoEntrega;
import br.com.fiapstore.entrega.domain.exception.EntregaNaoEncontradaException;
import br.com.fiapstore.entrega.domain.exception.OperacaoInvalidaException;
import br.com.fiapstore.entrega.domain.repository.IEntregaQueueAdapterIN;
import br.com.fiapstore.entrega.infra.messaging.entity.StatusPagamento;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AtualizacaoEntregaQueueAdapterIN implements IEntregaQueueAdapterIN {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Gson gson;

    private final ConfirmarAgendamentoEntrega confirmarAgendamentoEntrega;
    private final RegistrarAgendamentoEntrega registrarAgendamentoEntrega;


    @Autowired
    public AtualizacaoEntregaQueueAdapterIN(ConfirmarAgendamentoEntrega confirmarAgendamentoEntrega, RegistrarAgendamentoEntrega registrarAgendamentoEntrega){
        this.confirmarAgendamentoEntrega = confirmarAgendamentoEntrega;
        this.registrarAgendamentoEntrega = registrarAgendamentoEntrega;
    }

    @Override
    @RabbitListener(queues = {"${queue2.name}"})
    public void receive(@Payload String message) throws EntregaNaoEncontradaException, OperacaoInvalidaException {

        HashMap<String, String> mensagem = gson.fromJson(message, HashMap.class);

        EntregaDto entregaDto = IEntregaQueueAdapterIN.fromMessageToDto(mensagem);
        if(mensagem.get("statusPagamento").equals(StatusPagamento.PENDENTE.toString())){
            registrarAgendamentoEntrega.executar(entregaDto);
            logger.info("Operação registro de entrega executada", entregaDto);

        }else if(mensagem.get("statusPagamento").equals(StatusPagamento.REALIZADO.toString())) {
            confirmarAgendamentoEntrega.executar(null, entregaDto.getCodigoPedido());
            logger.info("Operação confirmação de entrega executada", entregaDto);
        }

    }



}
