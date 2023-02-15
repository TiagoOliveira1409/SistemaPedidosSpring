package io.github.SistemaPedidosSpring.domain.entity;

import io.github.SistemaPedidosSpring.domain.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="CLIENTE_ID")
    private Cliente cliente;

    @Column(name = "DATA_PEDIDO")
    private LocalDate dataPedido;

    @Column(name = "TOTAL",precision = 20,length = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name="Status")
    private StatusPedido StatusPedido;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itensPedidos;

}
