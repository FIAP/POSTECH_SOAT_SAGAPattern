package br.com.fiapstore.cobranca.infra.database;

import br.com.fiapstore.cobranca.infra.database.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPagamentoRepository extends JpaRepository<PagamentoEntity, Long> {

    PagamentoEntity findPagamentoEntityByCodigoPedido(String codigo);

    PagamentoEntity findPagamentoEntityByCodigo(String codigo);
}
