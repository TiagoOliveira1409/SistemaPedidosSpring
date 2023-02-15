package io.github.SistemaPedidosSpring.domain.repository;

import io.github.SistemaPedidosSpring.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface Clientes extends JpaRepository<Cliente,Integer> {

    @Query(value = "select c from Cliente c where c.nome like :nome")
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    @Query(value = " delete from Cliente c where c.nome =:nome")
    @Modifying
    void deletar(@Param("nome") String nome);

    List<Cliente> findByNomeLike(String nome);

    boolean existsByNome(String nome);
}
