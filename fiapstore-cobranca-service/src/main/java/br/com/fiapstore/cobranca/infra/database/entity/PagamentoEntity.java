package br.com.fiapstore.cobranca.infra.database.entity;

import br.com.fiapstore.cobranca.domain.entity.StatusPagamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String codigo;
    private String codigoPedido;
    private Double valor;
    private Double percentualDesconto;
    private String cpf;
    private LocalDate dataVencimento;
    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;

}
