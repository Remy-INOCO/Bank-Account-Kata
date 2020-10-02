package com.inoco.kata.api.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EntityScan("com.inoco.kata.api.model")
@EnableJpaRepositories("com.inoco.kata.api.repository")
@ComponentScan(basePackages = { "com.inoco.kata.api" })
@EnableSwagger2
public class KataApplication {

	public static void main(final String[] args) {
		SpringApplication.run(KataApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public Docket kataBankAccountApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.inoco.kata.api")).build().apiInfo(this.apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Swagger Bank account")
				.description("This is an API to get information about a bank account")
				.contact(new Contact("Argentin Rémy", "", "remy.argentin@inoco.fr")).build();
	}
}
