package br.com.fiapstore.pedido;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class FiapStorePedidoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FiapStorePedidoServiceApplication.class, args);
    }

}
