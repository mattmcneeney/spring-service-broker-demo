package com.servicebroker.servicebroker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.servicebroker.model.Catalog;
import org.springframework.cloud.servicebroker.model.Plan;
import org.springframework.cloud.servicebroker.model.ServiceDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Collections;

@SpringBootApplication
public class ServicebrokerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicebrokerApplication.class, args);
	}

    @Bean
    public Catalog catalog() {
        return new Catalog(Collections.singletonList(
            new ServiceDefinition(
                  "demo-service-broker",
                  "demo",
                  "An example service broker implementation",
                  true,
                  false,
                  Collections.singletonList(
                        new Plan("simple",
                              "default",
                              "This is a default  plan.",
                              null)),
                  null,
                  null,
                  null,
                  null)));
    }

}
