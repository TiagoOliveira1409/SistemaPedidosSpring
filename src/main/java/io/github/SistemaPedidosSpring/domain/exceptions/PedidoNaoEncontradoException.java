package io.github.SistemaPedidosSpring.domain.exceptions;

public class PedidoNaoEncontradoException extends RuntimeException{
    public PedidoNaoEncontradoException() {
        super("Pedido Não Encoitrado");
    }
}
