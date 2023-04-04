package io.github.SistemaPedidosSpring.security.jwt;

import io.github.SistemaPedidosSpring.VendasAplication;
import io.github.SistemaPedidosSpring.domain.entity.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    private Key key;

    public String gerarToken(Usuario usuario) {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        System.out.println(this.key);

        int expir = Integer.parseInt(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expir);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date dataExpiracao = Date.from( instant );

        Claims claims = Jwts.claims();

        claims.setExpiration(dataExpiracao);
        claims.put("email","email@gmail.com");
        claims.put("roles","admin");
        claims.put("sub",usuario.getLogin());


        return Jwts
                .builder()
                .setClaims( claims )
                .signWith(this.key)
                .compact();
    }

    private Claims obterClaims(String token) throws ExpiredJwtException {

        return Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValido(String token) {
        try {
            Claims claims = obterClaims(token);
            Date data = claims.getExpiration();
            String login = claims.getSubject();
            LocalDateTime expiration = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(expiration);
        }catch (Exception e){
            return false;
        }
    }

    public String obterLogin(String token) throws ExpiredJwtException{
        return obterClaims(token).getSubject();
    }
}
