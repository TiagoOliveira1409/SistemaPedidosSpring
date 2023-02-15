package io.github.SistemaPedidosSpring.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoDTO {
    private Integer produtoId;
    private Integer quantidade;
}
