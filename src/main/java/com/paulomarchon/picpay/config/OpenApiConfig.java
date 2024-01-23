package com.paulomarchon.picpay.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "PauloMarchon",
                        email = "example@email.com",
                        url = "https://example.com/teste"
                ),
                description = "Documentacao OpenApi para servico REST",
                title = "OpenApi - Paulo",
                version = "1.0",
                license = @License(
                        name = "MIT",
                        url = "https://license.com"
                ),
                termsOfService = "Termos e Servicos"
        ),
        servers = {
                @Server(
                        description = "Ambiente de desenvolvimento",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Ambiente de producao",
                        url = "http://endereco.com"
                )
        }
)
public class OpenApiConfig {

}
