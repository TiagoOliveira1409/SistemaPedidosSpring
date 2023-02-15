package io.github.SistemaPedidosSpring.domain.exceptions;

public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String message) {
        super(message);
    }
}
