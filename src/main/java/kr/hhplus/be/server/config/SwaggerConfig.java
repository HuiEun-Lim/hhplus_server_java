package kr.hhplus.be.server.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());}
    private Info apiInfo() {
        return new Info()
                .title("e-커머스 서비스 구현")
                .description("항해 플러스 백엔드 7기 e-커머스 서비스 구현")
                .version("1.0.0");}}
