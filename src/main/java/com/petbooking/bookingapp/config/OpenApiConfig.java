package com.petbooking.bookingapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.customizers.OpenApiCustomizer;

import java.util.List;

@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI bookingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pet Booking API")
                        .description("REST API for the pet booking system")
                        .version("v1.0")
                        .license(new License().name("MIT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    @Bean
    public OpenApiCustomizer publicEndpointsCustomizer() {
        return openApi -> {
            var paths = openApi.getPaths();
            if (paths == null) return;

            // helper: clear security for a specific HTTP method of a path
            java.util.function.BiConsumer<String, PathItem.HttpMethod> clear = (p, m) -> {
                var item = paths.get(p);
                if (item == null) return;
                var op = item.readOperationsMap().get(m);
                if (op != null) op.setSecurity(List.of());
            };

            // PUBLIC endpoints
            clear.accept("/api/auth/login", PathItem.HttpMethod.POST);     // login
            clear.accept("/api/users",       PathItem.HttpMethod.POST);     // register (create user)
            clear.accept("/api/rooms",       PathItem.HttpMethod.GET);      // get all rooms
            clear.accept("/api/rooms/{id}",  PathItem.HttpMethod.GET);      // get room by id
            clear.accept("/api/reviews",     PathItem.HttpMethod.GET);      // get all reviews
            clear.accept("/api/bookings/quote", PathItem.HttpMethod.POST);  // booking quote
            clear.accept("/api/rooms/{roomId}/calendar", PathItem.HttpMethod.GET); // room calendar
        };
    }
}
