package io.github.SistemaPedidosSpring.domain.exceptions;

public class SenhaInvalidaException extends RuntimeException{
    public SenhaInvalidaException(String message) {
        super(message);
    }
}
