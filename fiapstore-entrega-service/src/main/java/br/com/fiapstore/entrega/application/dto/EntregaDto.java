package br.com.fiapstore.entrega.application.dto;

import br.com.fiapstore.entrega.domain.entity.Entrega;
import br.com.fiapstore.entrega.domain.entity.StatusEntrega;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Entrega")
public class EntregaDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID codigo;

    @Schema(example = "xxxxxxxx-xxxxxx-xxxx-xxx-xxx-xxx-xx", requiredMode = Schema.RequiredMode.REQUIRED)
    private String codigoPedido;

    @Schema(example = "29460519008", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cpf;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String dataPrevistaEntrega;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String meioEntrega;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private StatusEntrega status;


    public static EntregaDto toEntregaDto(Entrega entrega){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return new EntregaDto(
                entrega.getCodigo(),
                entrega.getCodigoPedido(),
                entrega.getCpf(),
                formatter.format(entrega.getDataPrevistaEntrega()),
                entrega.getMeioEntrega(),
                entrega.getStatusEntrega()
        );

    }
}
