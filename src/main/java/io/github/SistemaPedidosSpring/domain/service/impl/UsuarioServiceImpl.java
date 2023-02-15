package io.github.SistemaPedidosSpring.domain.service.impl;

import io.github.SistemaPedidosSpring.domain.entity.Usuario;
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
    PasswordEncoder passwordEncoder;

    @Autowired
    UsuarioRepository usuarioRepository;

    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
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
                .password(passwordEncoder.encode(user.getSenha()))
                .roles(roles)
                .build();
    }

}
