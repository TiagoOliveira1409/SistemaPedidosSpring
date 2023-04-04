package io.github.SistemaPedidosSpring.domain.service.impl;

import io.github.SistemaPedidosSpring.domain.entity.Usuario;
import io.github.SistemaPedidosSpring.domain.exceptions.SenhaInvalidaException;
import io.github.SistemaPedidosSpring.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UserDetailsService{

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder encoder;

    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public void autenticar(Usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getLogin());

        boolean senhasBatem = encoder.matches(usuario.getSenha(), user.getPassword());

        if (!senhasBatem){
            throw new SenhaInvalidaException("Senha Inválida");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user =  usuarioRepository
                .findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado."));

        String[] roles = user.isAdmin() ?
                new String[]{"USER","ADMIN"} : new String[]{"USER"};

        return User
                .builder()
                .username(user.getLogin())
                .password(user.getSenha())
                .roles(roles)
                .build();
    }

}
