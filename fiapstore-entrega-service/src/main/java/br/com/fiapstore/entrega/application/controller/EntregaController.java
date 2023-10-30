package br.com.fiapstore.entrega.application.controller;

import br.com.fiapstore.entrega.application.dto.EntregaDto;
import br.com.fiapstore.entrega.domain.exception.EntregaNaoEncontradaException;
import br.com.fiapstore.entrega.domain.exception.OperacaoInvalidaException;
import br.com.fiapstore.entrega.domain.usecase.IRegistrarAgendamentoEntregaUseCase;
import br.com.fiapstore.entrega.domain.usecase.ICancelarAgendamentoEntregaUseCase;
import br.com.fiapstore.entrega.domain.usecase.IConfirmarAgendamentoEntregaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/entrega")
public class EntregaController {

    private final IRegistrarAgendamentoEntregaUseCase agendarEntregaUseCase;
    private final IConfirmarAgendamentoEntregaUseCase confirmarEntregaUseCase;
    private final ICancelarAgendamentoEntregaUseCase cancelarEntregaUseCase;

    @Autowired
    public EntregaController(IRegistrarAgendamentoEntregaUseCase agendarEntregaUseCase, IConfirmarAgendamentoEntregaUseCase confirmarEntregaUseCase, ICancelarAgendamentoEntregaUseCase cancelarEntregaUseCase) {
        this.agendarEntregaUseCase = agendarEntregaUseCase;
        this.confirmarEntregaUseCase = confirmarEntregaUseCase;
        this.cancelarEntregaUseCase = cancelarEntregaUseCase;
    }

    @PostMapping(produces = "application/json")
    @Operation(summary = "agendar")
    public ResponseEntity agendarEntrega(@RequestBody EntregaDto entregaDto) {
        try {
            EntregaDto entregaDtoSalva = agendarEntregaUseCase.executar(entregaDto);
            return ResponseEntity.status(HttpStatus.OK).body(entregaDtoSalva);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PatchMapping(value = "/confirmar/{codigo}",produces = "application/json")
    @Operation(summary = "confirmar")
    public ResponseEntity confirmarEntrega(@PathVariable String codigo) {
        try {
            EntregaDto entregaDtoSalva = confirmarEntregaUseCase.executar(codigo,null);
            return ResponseEntity.status(HttpStatus.OK).body(entregaDtoSalva);
        }catch ( OperacaoInvalidaException | EntregaNaoEncontradaException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PatchMapping(value = "/cancelar/{codigo}",produces = "application/json")
    @Operation(summary = "cancelar")
    public ResponseEntity cancelarEntrega(@PathVariable String codigo) {
        try {
            EntregaDto entregaDtoSalva = cancelarEntregaUseCase.executar(codigo);
            return ResponseEntity.status(HttpStatus.OK).body(entregaDtoSalva);
        }catch ( OperacaoInvalidaException | EntregaNaoEncontradaException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

}
