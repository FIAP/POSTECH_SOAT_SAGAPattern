package br.com.fiapstore.entrega;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class FiapstoreEntregaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FiapstoreEntregaServiceApplication.class, args);
	}

}
