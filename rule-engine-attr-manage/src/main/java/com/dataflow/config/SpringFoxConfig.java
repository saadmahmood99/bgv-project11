package com.dataflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.PathSelectors;

import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class SpringFoxConfig {

@Bean
public Docket api()
{
	return new Docket(DocumentationType.SWAGGER_2).select()
            .paths(PathSelectors.any())
            .apis(RequestHandlerSelectors.basePackage("com.dataflow.controllers"))
            .build();
}

}
