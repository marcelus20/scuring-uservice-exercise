package com.marcelus.securinguserviceexercise.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Gateway {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("health_route", r -> r.path("/health")
                        .uri("http://main-api-service:33333/health"))
                .route("auth_route", r-> r.path("/auth")
                        .uri("http://security-service:22222/auth"))
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Gateway.class, args);
    }
}
