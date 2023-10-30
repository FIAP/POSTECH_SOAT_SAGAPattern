package br.com.fiapstore.entrega.domain.usecase;

import br.com.fiapstore.entrega.application.dto.EntregaDto;

public interface IRegistrarAgendamentoEntregaUseCase {

    EntregaDto executar(EntregaDto entregaDto);

}
