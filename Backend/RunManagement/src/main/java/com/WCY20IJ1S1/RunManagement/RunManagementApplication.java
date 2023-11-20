package com.WCY20IJ1S1.RunManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class RunManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(RunManagementApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/DB/**")
						.allowedOrigins("http://127.0.0.1:5500")
						.allowedMethods("GET", "POST", "PUT", "DELETE");
			}
		};
	}

}
