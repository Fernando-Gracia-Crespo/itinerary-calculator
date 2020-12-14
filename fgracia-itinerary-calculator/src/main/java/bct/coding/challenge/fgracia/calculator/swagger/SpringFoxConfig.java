package bct.coding.challenge.fgracia.calculator.swagger;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()                                  
				.apis( RequestHandlerSelectors.basePackage("bct.coding.challenge.fgracia.calculator"))             
				.paths(PathSelectors.any())                          
				.build();  
	}
	
	
	private ApiInfo apiInfo() {
		return new ApiInfo("Itinerary Calculator", "Itinerary calculator", "1.0", "", new Contact("Fernando Gracia", "", "f.gracia.crespo@gmail.com"), "", "", Collections.emptyList());
	}
	
}
