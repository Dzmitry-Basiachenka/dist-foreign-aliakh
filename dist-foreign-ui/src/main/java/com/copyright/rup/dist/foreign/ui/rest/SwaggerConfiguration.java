package com.copyright.rup.dist.foreign.ui.rest;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration for Swagger UI.
 * <p>
 * Copyright (C) 2019 copyright.com
 * <p>
 * Date: 02/20/2019
 *
 * @author Aliaksanr Liakh
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    /**
     * Gets instance of {@link Docket}.
     *
     * @return instance of {@link Docket}
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("dist-foreign-ui-rest-api")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.copyright.rup.dist.foreign.ui.rest"))
            .paths(paths())
            .build()
            .apiInfo(apiInfo());
    }

    /**
     * Gets instance of {@link UiConfiguration}.
     *
     * @return instance of {@link UiConfiguration}
     */
    @Bean
    public UiConfiguration uiConfiguration() {
        return UiConfigurationBuilder.builder()
            .displayRequestDuration(Boolean.TRUE)
            .validatorUrl("")
            .build();
    }

    private Predicate<String> paths() {
        return Predicates.or(
            PathSelectors.regex("/jobs.*"),
            PathSelectors.regex("/statistic.*")
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Foreign Distributions (FDA) Processor REST")
            .description("Foreign Distributions (FDA) Processor REST API reference")
            .termsOfServiceUrl("http://copyright.com")
            .contact(new Contact("Copyright.com", "http://copyright.com", "copyright@copyright.com")).version("0.1")
            .build();
    }
}
