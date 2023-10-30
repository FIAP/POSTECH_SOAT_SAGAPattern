package br.com.fiapstore.cobranca;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class FiapstoreCobrancaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FiapstoreCobrancaServiceApplication.class, args);
	}

}
