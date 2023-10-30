package br.com.fiapstore.pedido.infra.database.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class CupomDescontoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String codigo;
    private LocalDate dataUso;
    private Double percentual;
    private LocalDate dataVencimento;


    public CupomDescontoEntity(String codigo, LocalDate dataUso, Double percentual, LocalDate dataVencimento) {
        this.codigo = codigo;
        this.dataUso = dataUso;
        this.percentual = percentual;
        this.dataVencimento = dataVencimento;
    }
}
