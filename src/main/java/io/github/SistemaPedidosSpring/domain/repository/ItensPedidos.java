package io.github.SistemaPedidosSpring.domain.repository;

import io.github.SistemaPedidosSpring.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItensPedidos extends JpaRepository<ItemPedido, Integer> {
}
