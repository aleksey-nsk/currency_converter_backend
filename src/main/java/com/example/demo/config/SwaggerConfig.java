package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact()
                .name("Aleksey Zhdanov")
                .email("aleksey.zhd@gmail.com")
                .url("https://github.com/aleksey-nsk");

        Info info = new Info()
                .title("Converter API") // название приложения
                .version("v1.0.0") // версия API
                .contact(contact); // разработчик API

        OpenAPI openAPI = new OpenAPI().info(info);

        return openAPI;
    }
}
