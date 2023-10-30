package br.com.fiapstore.entrega.application.usecase;

import br.com.fiapstore.entrega.application.dto.EntregaDto;
import br.com.fiapstore.entrega.domain.entity.Entrega;
import br.com.fiapstore.entrega.domain.exception.EntregaNaoEncontradaException;
import br.com.fiapstore.entrega.domain.exception.OperacaoInvalidaException;
import br.com.fiapstore.entrega.domain.repository.IEntregaDatabaseAdapter;
import br.com.fiapstore.entrega.domain.repository.IEntregaQueueAdapterOUT;
import br.com.fiapstore.entrega.domain.usecase.IConfirmarAgendamentoEntregaUseCase;
import org.springframework.stereotype.Service;

@Service
public class ConfirmarAgendamentoEntrega implements IConfirmarAgendamentoEntregaUseCase {

    private final IEntregaDatabaseAdapter entregaDatabaseAdapter;
    private final IEntregaQueueAdapterOUT entregaQueueAdapterOUT;

    public ConfirmarAgendamentoEntrega(IEntregaDatabaseAdapter entregaDatabaseAdapter, IEntregaQueueAdapterOUT entregaQueueAdapterOUT) {
        this.entregaDatabaseAdapter = entregaDatabaseAdapter;
        this.entregaQueueAdapterOUT = entregaQueueAdapterOUT;
    }

    @Override
    public EntregaDto executar(String codigoEntrega, String condigoPedido) throws OperacaoInvalidaException, EntregaNaoEncontradaException {
        Entrega entrega =null;

        if(codigoEntrega!=null)
            entrega =   this.entregaDatabaseAdapter.findByCodigo(codigoEntrega);
        else
            entrega =   this.entregaDatabaseAdapter.findByCodigoPedido(condigoPedido);

        if(entrega==null) throw new EntregaNaoEncontradaException("Entrega não encontrada");

        entrega.confirmarEntrega();

        entrega = entregaDatabaseAdapter.save(entrega);

        entregaQueueAdapterOUT.publishEntregaConfirmada(entrega);

        return EntregaDto.toEntregaDto(entrega);

    }
}
