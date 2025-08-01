package com.api.dataHub.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import java.util.List;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SwaggerConfig {

    @Value("${api.key}")
    private String apiKey;

    @Bean
    public OpenAPI dataHubApiDoc() {
        return new OpenAPI()
                .info(new Info()
                     // 타이틀, 버전, 설명
                    .title("Data API")
                    .version("v1.0")
                    .description("Open API Demo 상세 API 명세서입니다.")
//                     엔드포인트 서버 지정
//                    .servers(java.util.Arrays.asList(
//                    new Server().url("http://localhost:8080").description("로컬서버")
//                    new Server().url("http://localhost:8080").description("개발서버")
//                    )
                )
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("JWT 토큰을 입력하세요. (예 : {accessToken})"))
                        .addSecuritySchemes("apiKeyAuth", new SecurityScheme()
                                .type(Type.APIKEY)
                                .in(In.QUERY)
                                .name("apiKeyAuth")
                                .description("API Key를 입력하세요.")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth").addList("apiKeyAuth"));

    }

    // 보안 그룹 분류
    @Bean
    public GroupedOpenApi securedApiGroup() {
        return GroupedOpenApi.builder()
                .group("2-Defailt API")
                // 그룹 path
                .pathsToMatch("/dataHub/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicApiGroup() {
        return GroupedOpenApi.builder()
                .group("1-Public API")
                .pathsToMatch("/public/**")
                .addOperationCustomizer(((operation, handlerMethod) -> {
                    if (operation.getSecurity() != null) { // operation.getSecurity()가 null일 수 있으므로 null 체크
                        operation.getSecurity().removeIf(sr -> sr.containsKey("apiKeyAuth") || sr.containsKey("bearerAuth"));
                    }
                    return operation;
                }))
                .build();
    }
}
