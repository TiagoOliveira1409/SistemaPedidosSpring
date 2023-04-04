package io.github.SistemaPedidosSpring.rest.controller;

import io.github.SistemaPedidosSpring.domain.entity.Usuario;
import io.github.SistemaPedidosSpring.domain.exceptions.SenhaInvalidaException;
import io.github.SistemaPedidosSpring.domain.service.impl.UsuarioServiceImpl;
import io.github.SistemaPedidosSpring.rest.dto.CredenciaisDTO;
import io.github.SistemaPedidosSpring.rest.dto.TokenDTO;
import io.github.SistemaPedidosSpring.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuarios/")
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario CriarUsuario(@RequestBody @Valid Usuario usuario) {
        String SenhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(SenhaCriptografada);
        return usuarioService.save(usuario);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try {
            Usuario usuario= new Usuario();
            usuario.setLogin(credenciais.getLogin());
            usuario.setSenha(credenciais.getSenha());

            usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);

            return new TokenDTO(usuario.getLogin(), token);

        }catch (SenhaInvalidaException | UsernameNotFoundException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
