package io.github.SistemaPedidosSpring.domain.service.impl;

import io.github.SistemaPedidosSpring.domain.entity.Cliente;
import io.github.SistemaPedidosSpring.domain.entity.ItemPedido;
import io.github.SistemaPedidosSpring.domain.entity.Pedido;
import io.github.SistemaPedidosSpring.domain.entity.Produto;
import io.github.SistemaPedidosSpring.domain.enums.StatusPedido;
import io.github.SistemaPedidosSpring.domain.exceptions.PedidoNaoEncontradoException;
import io.github.SistemaPedidosSpring.domain.exceptions.RegraNegocioException;
import io.github.SistemaPedidosSpring.domain.repository.Clientes;
import io.github.SistemaPedidosSpring.domain.repository.ItensPedidos;
import io.github.SistemaPedidosSpring.domain.repository.Pedidos;
import io.github.SistemaPedidosSpring.domain.repository.Produtos;
import io.github.SistemaPedidosSpring.domain.service.PedidoService;
import io.github.SistemaPedidosSpring.rest.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {


    private final Pedidos pedidosRepository;
    private final Clientes clientesRepo;
    private final Produtos produtosRepo;
    private final ItensPedidos itensPedidosRepository;

    @Override
    @Transactional
    public Integer salvar(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();

        Cliente cliente = clientesRepo
                .findById(pedidoDTO.getClienteId())
                .orElseThrow(() -> new RegraNegocioException("Cliente não encontrado"));

        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDate.now());
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setStatusPedido(StatusPedido.REALIZADO);

        List<ItemPedido> ListaProdutos = converterItens(pedidoDTO.getItensPedido(),pedido);

        pedidosRepository.save(pedido);
        itensPedidosRepository.saveAll(ListaProdutos);
        pedido.setItensPedidos(ListaProdutos);

        return pedido.getId();
    }

    @Override
    public informacoesPedidoDTO obterinformacoesPedidoDTO(Integer id) {
        return converterPedidoParaDTO(
                pedidosRepository
                        .findByIdFetchItens(id)
                        .orElseThrow(() -> new RegraNegocioException("Pedido não encontrado"))
        );
    }

    @Override
    public void atualizaStatus(Integer id, AtualizaStatusDTO novoStatus) {
        pedidosRepository
                .findById(id)
                .map(pedido -> {
                    StatusPedido status = StatusPedido.valueOf(novoStatus.getNovoStatus());
                    pedido.setStatusPedido(status);
                    return pedidosRepository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException());
    }

    private informacoesPedidoDTO converterPedidoParaDTO(Pedido pedido){
        return informacoesPedidoDTO.builder()
                .codigo(pedido.getId())
                .nomeCliente(pedido.getCliente().getNome())
                .cpfCliente(pedido.getCliente().getCpf())
                .total(pedido.getTotal())
                .status(pedido.getStatusPedido().name())
                .data(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .itens(converterItemPedidoParaDTO(pedido.getItensPedidos()))
                .build();
    }

    private List<informacaoItemPedidoDTO> converterItemPedidoParaDTO(List<ItemPedido> itemPedidoList){
        if(itemPedidoList.isEmpty()){
            return Collections.emptyList();
        }

        return itemPedidoList.stream().map(item ->
                    informacaoItemPedidoDTO.builder()
                            .descricaoProduto(item.getProduto().getDescricao())
                            .precoUnitario(item.getProduto().getPreco_unitario())
                            .quantidade(item.getQuantidade())
                            .build()
                ).collect(Collectors.toList());
    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


    private List<ItemPedido> converterItens(List<ItemPedidoDTO> itensPedidoDTO,Pedido pedido){
        if(itensPedidoDTO.isEmpty()){
            throw new RegraNegocioException("Náo é possivel criar um pedido sem itens.");
        }

        return itensPedidoDTO
                .stream()
                .map(itemDTO ->{
                    ItemPedido item = new ItemPedido();
                    item.setPedido(pedido);
                    item.setQuantidade(itemDTO.getQuantidade());
                    Produto produto = produtosRepo
                            .findById(itemDTO.getProdutoId())
                            .orElseThrow(() -> new RegraNegocioException("Produto não encontrado"));
                    item.setProduto(produto);
                    return item;
                }).collect(Collectors.toList());
    }

}
