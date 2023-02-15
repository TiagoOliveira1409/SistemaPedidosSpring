package io.github.SistemaPedidosSpring.rest.controller;

import io.github.SistemaPedidosSpring.domain.entity.Usuario;
import io.github.SistemaPedidosSpring.domain.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuarios/")
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario CriarUsuario(@RequestBody @Valid Usuario usuario) {
        String SenhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(SenhaCriptografada);
        return usuarioService.save(usuario);
    }
}
