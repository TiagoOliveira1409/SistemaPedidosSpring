package io.github.SistemaPedidosSpring.domain.service;

import io.github.SistemaPedidosSpring.rest.dto.AtualizaStatusDTO;
import io.github.SistemaPedidosSpring.rest.dto.PedidoDTO;
import io.github.SistemaPedidosSpring.rest.dto.informacoesPedidoDTO;
import org.springframework.stereotype.Service;

@Service
public interface PedidoService {

    Integer salvar(PedidoDTO pedido);

    informacoesPedidoDTO obterinformacoesPedidoDTO(Integer id);

    void atualizaStatus(Integer id, AtualizaStatusDTO novoStatus);

}
