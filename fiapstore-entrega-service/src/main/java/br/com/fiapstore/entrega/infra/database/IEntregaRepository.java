package br.com.fiapstore.entrega.infra.database;

import br.com.fiapstore.entrega.infra.database.entity.EntregaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEntregaRepository extends JpaRepository<EntregaEntity, Long> {

    EntregaEntity findEntregaEntityByCodigo(String codigo);

    EntregaEntity findEntregaEntityByCodigoPedido(String codigo);
}
