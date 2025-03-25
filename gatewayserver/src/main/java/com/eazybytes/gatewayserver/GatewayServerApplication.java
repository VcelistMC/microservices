package com.eazybytes.gatewayserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}

	@Bean
	public RouteLocator eazybankRouteConfig(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
					.path("/eazybank/accounts/**")
					.filters(f -> f
							.rewritePath("/eazybank/accounts/(?<segment>.*)", "/${segment}")
							.addRequestHeader("X-Response-Time", LocalDateTime.now().toString())
							.circuitBreaker(config -> config
									.setName("accountsCircuitBreaker")
									.setFallbackUri("forward:/contactSupport")
							)

					)
					.uri("lb://ACCOUNTS"))

				.route(p -> p
					.path("/eazybank/loans/**")
					.filters(f -> f
							.rewritePath("/eazybank/loans/(?<segment>.*)", "/${segment}")
							.addRequestHeader("X-Response-Time", LocalDateTime.now().toString())
					)
					.uri("lb://LOANS"))
				.route(p -> p
					.path("/eazybank/cards/**")
					.filters(f -> f
							.rewritePath("/eazybank/cards/(?<segment>.*)", "/${segment}")
							.addRequestHeader("X-Response-Time", LocalDateTime.now().toString())
					)
					.uri("lb://CARDS"))
				.build();
	}


}
