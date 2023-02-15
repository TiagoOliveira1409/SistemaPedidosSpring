package io.github.SistemaPedidosSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class VendasAplication {

    public static void main(String[] args) {
        SpringApplication.run(VendasAplication.class ,args);
    }
}
