package com.dataflow.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan({"com.dataflow.controllers","com.dataflow.services"})
@EntityScan("com.dataflow.models")
@EnableJpaRepositories("com.dataflow.repositories")
@EnableWebMvc
@EnableEurekaClient
public class RuleEngineAttrManageApplication {
	public static void main(String[] args) {
		SpringApplication.run(RuleEngineAttrManageApplication.class, args); 
	}
	
	@Bean
	@LoadBalanced
	RestTemplate restTemplateBean(RestTemplateBuilder builder)
	{
		return builder.build();
	}

}
