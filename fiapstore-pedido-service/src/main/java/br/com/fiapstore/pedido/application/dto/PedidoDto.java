package br.com.fiapstore.pedido.application.dto;

import br.com.fiapstore.pedido.domain.entity.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Pedido")
public class PedidoDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String codigoPedido;

    @Schema(example = "29460519008", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cpf;

    @Schema(example = "TESTE100", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String codigoCupom;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private StatusPedido statusPedido;

    @Schema( example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long codigoProduto;

    @Schema(example = "2",  requiredMode = Schema.RequiredMode.REQUIRED)
    private Long quantidadeProduto;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Double precoTotal;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Double precoTotalComDesconto;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Double percentualDescontoAplicado;

}
