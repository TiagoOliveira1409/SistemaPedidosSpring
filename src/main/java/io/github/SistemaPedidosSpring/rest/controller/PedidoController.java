package io.github.SistemaPedidosSpring.rest.controller;

import io.github.SistemaPedidosSpring.domain.service.PedidoService;
import io.github.SistemaPedidosSpring.rest.dto.AtualizaStatusDTO;
import io.github.SistemaPedidosSpring.rest.dto.PedidoDTO;
import io.github.SistemaPedidosSpring.rest.dto.informacoesPedidoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/pedidos")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO pedido){
        return pedidoService.salvar(pedido);
    }

    @GetMapping("{id}")
    public informacoesPedidoDTO getById(@PathVariable Integer id){
        return pedidoService.obterinformacoesPedidoDTO(id);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizaStatus(@PathVariable Integer id, @RequestBody AtualizaStatusDTO novoStatus){
        pedidoService.atualizaStatus(id,novoStatus);
    }
}
