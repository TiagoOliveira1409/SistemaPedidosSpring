package io.github.SistemaPedidosSpring.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredenciaisDTO {

    private String Login;
    private String Senha;
}
