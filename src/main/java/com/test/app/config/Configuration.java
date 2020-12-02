package com.test.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@org.springframework.context.annotation.Configuration
public class Configuration {

    @SuppressWarnings("deprecation")
	private final class WebMvcConfigurerAdapterExtension extends WebMvcConfigurerAdapter {
		@Override
		public void addCorsMappings(CorsRegistry registry) {
		    registry.addMapping("/**")
		            .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
		}
	}

	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapterExtension();
    }
}