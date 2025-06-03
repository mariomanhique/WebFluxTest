package com.mariomanhique.webfluxtest;

import io.r2dbc.spi.ConnectionFactory;
import reactor.core.publisher.Mono;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.core.DatabaseClient;

/**
 * Application configuration defining a
 *
 * @author Mark Paluch
 */
@SpringBootApplication
class ApplicationConfiguration {

	@Bean
	BeforeConvertCallback<Note> idGeneratingCallback(DatabaseClient databaseClient) {

		return (customer, sqlIdentifier) -> {

			if (customer.id() == null) {

				return databaseClient.sql("SELECT NEXT VALUE FOR primary_key") //
						.map(row -> row.get(0, Long.class)) //
						.first() //
						.map(customer::withId);
			}

			return Mono.just(customer);
		};
	}

	@Bean
	ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

		var initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
		initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ByteArrayResource(("CREATE SEQUENCE primary_key;"
				+ "DROP TABLE IF EXISTS customer;"
				+ "CREATE TABLE customer (id INT PRIMARY KEY, firstname VARCHAR(100) NOT NULL, lastname VARCHAR(100) NOT NULL);")
						.getBytes())));

		return initializer;
	}
}