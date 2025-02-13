package com.banco.xyz.financeiro.infrastructure;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguracao {


    @Bean
    public OpenAPI configuracaoOpenApi(){

        return new OpenAPI()
                .info(new Info()
                        .title("API Banco XYZ")
                        .description("API do Banco XYZ para cadastro de novos clientes e trasações financeiras")
                        .version("1.0"))
                .components(new Components()
                        .addSecuritySchemes("bearerAPI", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                );
    }
}
