package org.sakila;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SpringBootApplication
public class SbSakilaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbSakilaApplication.class, args);
	}

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        // Asegúrate de cambiar 'sakila', 'root' y 'tu_contraseña' por tus datos reales
        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila?useSSL=false&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }
}
