package br.com.fiapstore.cobranca.application.dto;

import br.com.fiapstore.cobranca.domain.entity.Pagamento;
import br.com.fiapstore.cobranca.domain.entity.StatusPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Pagamento")
public class PagamentoDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String codigo;

    @Schema(example = "xxxxxxxx-xxxxxx-xxxx-xxx-xxx-xxx-xx", requiredMode = Schema.RequiredMode.REQUIRED)
    private String codigoPedido;

    @Schema(example = "2500.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double valor;

    @Schema(example = "10", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Double percentualDesconto;


    @Schema(example = "29460519008", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cpf;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String dataVencimento;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private StatusPagamento statusPagamento;



    @JsonIgnore
    public static PagamentoDto toPagamentoDto(Pagamento pagamento){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return new PagamentoDto(
                pagamento.getCodigo().toString(),
                pagamento.getCodigoPedido(),
                pagamento.getValor(),
                pagamento.getPercentualDesconto(),
                pagamento.getCpf(),
                formatter.format(pagamento.getDataVencimento()),
                pagamento.getStatusPagamento());
    }

}
