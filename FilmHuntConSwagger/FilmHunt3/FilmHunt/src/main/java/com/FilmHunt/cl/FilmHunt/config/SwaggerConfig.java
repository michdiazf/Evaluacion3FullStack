package com.FilmHunt.cl.FilmHunt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
            .info(new Info()
            .title("Api administración de FilmHunt")
            .version("1.1")
            .description("Con esta API se puede administrar las peliculas de FilmHunt, incluyendo la creación, actualización y eliminación de Peliculas.")
        );
    }

}
