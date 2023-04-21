package com.bookingSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BackendApplication {
    
    public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Configuration
	public class CrossOriginConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");
            }
        };
    }

}

    // @Bean
	// public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
	// 	return args -> {
	// 		System.out.println("Let's inspect the beans provided by Spring Boot:");

	// 		String[] beanNames = ctx.getBeanDefinitionNames();
	// 		Arrays.sort(beanNames);
	// 		for (String beanName : beanNames) {
	// 			System.out.println(beanName);
	// 		}
	// 	};
	// }
}
