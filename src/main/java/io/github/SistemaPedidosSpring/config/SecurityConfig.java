package io.github.SistemaPedidosSpring.config;

import io.github.SistemaPedidosSpring.domain.service.impl.UsuarioServiceImpl;
import io.github.SistemaPedidosSpring.security.jwt.JwtAuthFilter;
import io.github.SistemaPedidosSpring.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private JwtService jwtService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService,usuarioService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected  void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                    .authorizeRequests()
                        .antMatchers("/api/clientes/**")
                            .hasRole("USER")
                        .antMatchers("/api/produtos/**")
                            .hasRole("ADMIN")
                        .antMatchers("/api/pedidos/**")
                            .hasRole("USER")
                        .antMatchers(HttpMethod.POST,"/api/usuarios/**")
                            .permitAll()
                        .anyRequest().authenticated()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
