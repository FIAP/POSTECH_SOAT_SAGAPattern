package br.com.fiapstore.cobranca.infra.messaging;

import br.com.fiapstore.cobranca.application.dto.PagamentoDto;
import br.com.fiapstore.cobranca.application.usecase.RegistrarPagamento;
import br.com.fiapstore.cobranca.domain.repository.IPagamentoQueueAdapter;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PagamentoQueueAdapterIN  {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Gson gson;

    private final RegistrarPagamento registrarPagamento;

    @Autowired
    public PagamentoQueueAdapterIN(RegistrarPagamento registrarPagamento){
        this.registrarPagamento = registrarPagamento;
    }

    @RabbitListener(queues = {"${queue1.name}"})
    public void receive(@Payload String message) {

        HashMap<String, String> mensagem = gson.fromJson(message, HashMap.class);
        PagamentoDto pagamentoDto = fromMessageToDto(mensagem);

        registrarPagamento.executar(pagamentoDto);
        logger.info("Pagamento registrado",pagamentoDto);
    }

    private static PagamentoDto fromMessageToDto(Map mensagem) {
        return new PagamentoDto(
                null,
                (String)mensagem.get("codigoPedido"),
                (Double)mensagem.get("precoTotal"),
                (Double)mensagem.get("percentualDesconto"),
                (String)mensagem.get("cpf"),
                null,
                null);
    }
}
