package com.rentabilidade_service.app_investimento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RentabilidadeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentabilidadeServiceApplication.class, args);
	}

}
