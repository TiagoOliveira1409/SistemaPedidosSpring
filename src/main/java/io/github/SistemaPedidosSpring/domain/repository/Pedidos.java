package io.github.SistemaPedidosSpring.domain.repository;

import io.github.SistemaPedidosSpring.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Pedidos extends JpaRepository<Pedido, Integer> {

    @Query("select p from Pedido p left join fetch p.itensPedidos where p.id = :id")
    Optional<Pedido> findByIdFetchItens(@Param("id") Integer id);
}
