package org.souzatech.tasks.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("http://localhost:8080")
    private String Url;

    @Bean
    public OpenAPI attornatusOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(Url);
        devServer.setDescription("URL do servidor no ambiente de Desenvolvimento");

        Contact contact = new Contact();
        contact.setEmail("fhssouza@gmail.com");
        contact.setName("Fábio Souza");

        License mit = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Perinity Cadastro de Tarefas - API")
                .version("1.0")
                .contact(contact)
                .description("Teste Técnico Back End - Perinity").termsOfService("")
                .license(mit);

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
