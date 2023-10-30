package br.com.fiapstore.cobranca.infra.messaging;

import br.com.fiapstore.cobranca.application.dto.PagamentoDto;
import br.com.fiapstore.cobranca.application.usecase.RegistrarPagamento;
import br.com.fiapstore.cobranca.infra.messaging.entity.MensagemFila;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PagamentoQueueAdapterIN  {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private final RegistrarPagamento registrarPagamento;

    @Autowired
    public PagamentoQueueAdapterIN(RegistrarPagamento registrarPagamento){
        this.registrarPagamento = registrarPagamento;
    }

    @RabbitListener(queues = {"${queue2.name}"})
    public void receive(@Payload String mensagemDaFila) {
        HashMap<String, String> mensagemFila = MensagemFila.fromJson(mensagemDaFila);
        logger.error(mensagemDaFila);
        logger.error("-------------");
        logger.error(mensagemFila.toString());

        PagamentoDto pagamentoDto = fromMensagemToDto(mensagemFila);
        registrarPagamento.executar(pagamentoDto);

        logger.info("Pagamento registrado",pagamentoDto);
    }

    private static PagamentoDto fromMensagemToDto(HashMap<String, String> mensagemFila) {
        return new PagamentoDto(
                null,
                mensagemFila.get("codigoPedido"),
                Double.parseDouble(mensagemFila.get("precoTotal")),
                Double.parseDouble(mensagemFila.get("percentualDesconto")),
                mensagemFila.get("cpf"),
                null,
                null);
    }
}
