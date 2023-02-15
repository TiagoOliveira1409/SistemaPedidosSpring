package io.github.SistemaPedidosSpring.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = "{campo.nome.obrigatorio}")
    @Column(name = "nome",length = 100)
    private String nome;

    @NotEmpty(message = "{campo.cpf.obrigatorio}")
    @CPF(message = "{campo.cpf.invalido}")
    @Column(name = "cpf",length = 11)
    private String cpf;


    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private Set<Pedido> pedidos;

}
