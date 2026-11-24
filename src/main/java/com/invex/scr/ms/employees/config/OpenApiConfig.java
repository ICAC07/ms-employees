package com.invex.scr.ms.employees.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class OpenApiConfig {

  static final String SCHEME = "basicAuth";

  @Bean
  OpenAPI employeeOpenApi() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Employee MS")
                .version("v1")
                .description("API de gestión de empleados"))
        .components(
            new Components()
                .addSecuritySchemes(
                    SCHEME, new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
        .addSecurityItem(new SecurityRequirement().addList(SCHEME));
  }
}
