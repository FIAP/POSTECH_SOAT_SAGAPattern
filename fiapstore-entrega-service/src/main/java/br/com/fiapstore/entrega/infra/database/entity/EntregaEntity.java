package br.com.fiapstore.entrega.infra.database.entity;

import br.com.fiapstore.entrega.domain.entity.StatusEntrega;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntregaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String codigo;

    private String codigoPedido;

    private String cpf;

    private LocalDate dataPrevistaEntrega;

    private String meioEntrega;

    @Enumerated(EnumType.STRING)
    private StatusEntrega statusEntrega;
}
