package io.github.SistemaPedidosSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@RestController
public class VendasAplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(VendasAplication.class ,args);
    }
}
