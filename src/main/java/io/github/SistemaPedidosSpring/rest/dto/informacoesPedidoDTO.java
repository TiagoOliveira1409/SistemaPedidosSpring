package io.github.SistemaPedidosSpring.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class informacoesPedidoDTO {
    private Integer codigo;
    private String nomeCliente;
    private String cpfCliente;
    private BigDecimal total;
    private String data;
    private String status;
    private List<informacaoItemPedidoDTO> itens;
}
