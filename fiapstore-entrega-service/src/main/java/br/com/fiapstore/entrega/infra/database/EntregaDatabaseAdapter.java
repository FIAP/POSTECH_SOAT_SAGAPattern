package br.com.fiapstore.entrega.infra.database;

import br.com.fiapstore.entrega.domain.entity.Entrega;
import br.com.fiapstore.entrega.domain.repository.IEntregaDatabaseAdapter;
import br.com.fiapstore.entrega.infra.database.entity.EntregaEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class EntregaDatabaseAdapter implements IEntregaDatabaseAdapter {

    @Autowired
    private final IEntregaRepository entregaRepository;


    @Override
    public Entrega save(Entrega entrega) {
        EntregaEntity entregaEntity = entregaRepository.save(toEntregaEntity(entrega));
        return toEntrega(entregaEntity);
    }

    @Override
    public Entrega findByCodigo(String codigo) {
        return toEntrega(entregaRepository.findEntregaEntityByCodigo(codigo));
    }

    @Override
    public Entrega findByCodigoPedido(String codigoPedido) {
        return toEntrega(entregaRepository.findEntregaEntityByCodigoPedido(codigoPedido));
    }


    private static EntregaEntity toEntregaEntity(Entrega entrega){
        EntregaEntity entregaEntity = null;

        if(entrega!=null) {
            entregaEntity = new EntregaEntity(
                    entrega.getId(),
                    entrega.getCodigo().toString(),
                    entrega.getCodigoPedido(),
                    entrega.getCpf(),
                    entrega.getDataPrevistaEntrega(),
                    entrega.getMeioEntrega(),
                    entrega.getStatusEntrega()
            );
        }
        return entregaEntity;
    }

    private static Entrega toEntrega(EntregaEntity entregaEntity){
        Entrega entrega = null;

        if(entregaEntity != null) {
            entrega = new Entrega(
                    entregaEntity.getId(),
                    UUID.fromString(entregaEntity.getCodigo()),
                    entregaEntity.getCodigoPedido(),
                    entregaEntity.getCpf(),
                    entregaEntity.getDataPrevistaEntrega(),
                    entregaEntity.getMeioEntrega(),
                    entregaEntity.getStatusEntrega());
        }
        return entrega;
     }
}
