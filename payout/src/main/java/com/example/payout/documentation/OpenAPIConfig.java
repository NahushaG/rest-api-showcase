package com.example.payout.documentation;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Payout Management API")
                        .version("1.0")
                        .description("API for managing payouts ...")
                );
    }

/*    @Bean
    public OpenAPI apiWithAuth() {
        return new OpenAPI()
                .info(new Info()
                        .title("Payout API")
                        .version("1.0")
                        .description("Payout Management API")
                ).components(new Components()
                        .addSecuritySchemes("ApiKeyAuth",
                                new SecurityScheme().type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name("X-API-KEY")
                        )
                ).addSecurityItem(new SecurityRequirement().addList("ApiKeyAuth"));
    }*/
}

