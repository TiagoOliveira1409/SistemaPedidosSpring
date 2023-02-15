package io.github.SistemaPedidosSpring.domain.repository;

import io.github.SistemaPedidosSpring.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Produtos extends JpaRepository<Produto, Integer> {
}
