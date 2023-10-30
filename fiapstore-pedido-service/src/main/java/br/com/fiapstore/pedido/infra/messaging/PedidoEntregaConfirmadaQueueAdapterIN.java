package br.com.fiapstore.pedido.infra.messaging;

import br.com.fiapstore.pedido.domain.exception.OperacaoInvalidaException;
import br.com.fiapstore.pedido.domain.exception.PedidoNaoEncontradoException;
import br.com.fiapstore.pedido.domain.exception.PercentualDescontoAcimaDoLimiteException;
import br.com.fiapstore.pedido.domain.repository.IPedidoEntregaConfirmadaQueueAdapterIN;
import br.com.fiapstore.pedido.domain.usecase.ConfirmarPedidoUseCase;
import br.com.fiapstore.pedido.domain.entity.StatusPedido;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PedidoEntregaConfirmadaQueueAdapterIN implements IPedidoEntregaConfirmadaQueueAdapterIN {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Gson gson;

    private final ConfirmarPedidoUseCase confirmarPedidoUseCase;

    @Autowired
    public PedidoEntregaConfirmadaQueueAdapterIN(ConfirmarPedidoUseCase confirmarPedidoUseCase){
        this.confirmarPedidoUseCase = confirmarPedidoUseCase;
    }

    @RabbitListener(queues = {"${queue1.name}"})
    public void receive(@Payload String message) throws PedidoNaoEncontradoException, PercentualDescontoAcimaDoLimiteException, OperacaoInvalidaException {

        HashMap<String, String> mensagem = gson.fromJson(message, HashMap.class);
        if(mensagem.get("statusPedido").equals(StatusPedido.CONFIRMADO.toString())) {
            confirmarPedidoUseCase.executar(mensagem.get("codigoPedido"));
            logger.info("Confirmação de pedido registrado");
        }
    }

}
