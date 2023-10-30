package br.com.fiapstore.pedido.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Produto")
public class ProdutoDto {

    @Schema(example = "1",accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(example = "Notebook",accessMode = Schema.AccessMode.READ_ONLY)
    private String nome;

    @Schema(example = "2500.00",accessMode = Schema.AccessMode.READ_ONLY)
    private Double preco;
}
