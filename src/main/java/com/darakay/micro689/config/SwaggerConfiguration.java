package com.darakay.micro689.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;

import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(Authentication.class)
                .select().apis(withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiEndPointsInfo())
                .securitySchemes(Arrays.asList(basicAuthScheme()))
                .securityContexts(Arrays.asList(blSecurityContext(), authSecurityContext()));
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Micro689 REST API")
                .description("REST API для сервиса \"Черные списки\"")
                .version("1.2.6")
                .build();
    }

    private SecurityContext blSecurityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(basicAuthReference()))
                .forPaths(PathSelectors.ant("/api/v1/black-list/**"))
                .build();
    }

    private SecurityContext authSecurityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(basicAuthReference()))
                .forPaths(PathSelectors.ant("/api/v1/login"))
                .build();
    }

    private SecurityScheme basicAuthScheme() {
        return new BasicAuth("basicAuth");
    }

    private SecurityReference basicAuthReference() {
        return new SecurityReference("basicAuth", new AuthorizationScope[0]);
    }
}
