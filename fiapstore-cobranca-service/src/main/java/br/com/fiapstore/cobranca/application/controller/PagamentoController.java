package br.com.fiapstore.cobranca.application.controller;

import br.com.fiapstore.cobranca.application.dto.PagamentoDto;
import br.com.fiapstore.cobranca.domain.exception.OperacaoInvalidaException;
import br.com.fiapstore.cobranca.domain.exception.PagamentoNaoEncontradoException;
import br.com.fiapstore.cobranca.domain.usecase.ICancelarPagamentoUseCase;
import br.com.fiapstore.cobranca.domain.usecase.IConfirmarPagamentoUseCase;
import br.com.fiapstore.cobranca.domain.usecase.IConsultarPagamentoUseCase;
import br.com.fiapstore.cobranca.domain.usecase.IRegistrarPagamentoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagamento")
public class PagamentoController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private IRegistrarPagamentoUseCase registrarPagamentoUseCase;
    private ICancelarPagamentoUseCase cancelarPagamentoUseCase;
    private IConsultarPagamentoUseCase consultarPagamentoUseCase;
    private IConfirmarPagamentoUseCase confirmarPagamentoUseCase;

    @Autowired
    public PagamentoController(IRegistrarPagamentoUseCase IRegistrarPagamentoUseCase, ICancelarPagamentoUseCase cancelarPagamentoUseCase, IConsultarPagamentoUseCase consultarPagamentoUseCase, IConfirmarPagamentoUseCase confirmarPagamentoUseCase) {
        this.registrarPagamentoUseCase = IRegistrarPagamentoUseCase;
        this.cancelarPagamentoUseCase = cancelarPagamentoUseCase;
        this.consultarPagamentoUseCase = consultarPagamentoUseCase;
        this.confirmarPagamentoUseCase = confirmarPagamentoUseCase;
    }

    @PostMapping(produces = "application/json")
    @Operation(summary = "realizar pagamento")
    public ResponseEntity realizarPagamento(@RequestBody PagamentoDto pagamentoDto) {
        try {
            pagamentoDto = this.registrarPagamentoUseCase.executar(pagamentoDto);
            return ResponseEntity.status(HttpStatus.OK).body(pagamentoDto);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }



    @GetMapping(value = "/{codigo}",produces = "application/json")
    @Operation(summary = "consultar pagamento")
    public ResponseEntity consultarPagamentoPorCodigo(@PathVariable String codigo) {
        PagamentoDto pagamentoDto = null;
        try {
            pagamentoDto = this.consultarPagamentoUseCase.executar(codigo);
            return ResponseEntity.status(HttpStatus.OK).body(pagamentoDto);
        } catch (PagamentoNaoEncontradoException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }


    @PatchMapping(value = "/cancelar/{codigo}",produces = "application/json")
    @Operation(summary = "cancelar pagamento")
    public ResponseEntity cancelarPagamento(@PathVariable String codigo) {
        PagamentoDto dto = null;
        try {
            dto = this.cancelarPagamentoUseCase.executar(codigo);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (PagamentoNaoEncontradoException |OperacaoInvalidaException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

    }

    @PatchMapping(value = "/confirmar/{codigo}",produces = "application/json")
    @Operation(summary = "confirmar pagamento")
    public ResponseEntity confirmarPagamento(@PathVariable String codigo) {
        PagamentoDto dto = null;
        try {
            dto = this.confirmarPagamentoUseCase.executar(codigo);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (PagamentoNaoEncontradoException |OperacaoInvalidaException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

    }

}
