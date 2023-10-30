package br.com.fiapstore.pedido.application.controller;

import br.com.fiapstore.pedido.application.dto.PedidoDto;
import br.com.fiapstore.pedido.domain.exception.OperacaoInvalidaException;
import br.com.fiapstore.pedido.domain.exception.PercentualDescontoAcimaDoLimiteException;
import br.com.fiapstore.pedido.domain.exception.PedidoNaoEncontradoException;
import br.com.fiapstore.pedido.domain.usecase.CancelarPedidoUseCase;
import br.com.fiapstore.pedido.domain.usecase.ConfirmarPedidoUseCase;
import br.com.fiapstore.pedido.domain.usecase.RegistrarPedidoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RegistrarPedidoUseCase registrarPedidoUseCase;
    private final CancelarPedidoUseCase cancelarPedidoUseCase;
    private final ConfirmarPedidoUseCase confirmarPedidoUseCase;

    @Autowired
    public PedidoController(RegistrarPedidoUseCase registrarPedidoUseCase, CancelarPedidoUseCase cancelarPedidoUseCase, ConfirmarPedidoUseCase confirmarPedidoUseCase) {
        this.registrarPedidoUseCase = registrarPedidoUseCase;
        this.cancelarPedidoUseCase = cancelarPedidoUseCase;
        this.confirmarPedidoUseCase = confirmarPedidoUseCase;
    }

    @PostMapping(produces = "application/json")
    @Operation(summary = "criar pedido")
    public ResponseEntity criarPedido(@RequestBody PedidoDto pedidoDto) {
        try {
            PedidoDto pedidoDtoSalvo = registrarPedidoUseCase.executar(pedidoDto);
            return ResponseEntity.status(HttpStatus.OK).body(pedidoDtoSalvo);
        } catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PatchMapping(value = "/cancelar/{codigo}",produces = "application/json")
    @Operation(summary = "cancelar pedido")
    public ResponseEntity cancelarPedido(@PathVariable String codigo) {
        try {
            cancelarPedidoUseCase.executar(codigo);
            return ResponseEntity.status(HttpStatus.OK).body("Sucesso");
        } catch (PedidoNaoEncontradoException | PercentualDescontoAcimaDoLimiteException | OperacaoInvalidaException ex) {
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PatchMapping(value = "/confirmar/{codigo}",produces = "application/json")
    @Operation(summary = "confirmar pedido")
    public ResponseEntity confirmarPedido(@PathVariable String codigo) {
        try {
            confirmarPedidoUseCase.executar(codigo);
            return ResponseEntity.status(HttpStatus.OK).body("Sucesso");
        } catch (PedidoNaoEncontradoException | PercentualDescontoAcimaDoLimiteException | OperacaoInvalidaException ex ) {
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }catch (Exception ex) {
            logger.error(ex.getMessage(),ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
