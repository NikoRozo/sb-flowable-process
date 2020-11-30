package com.test.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.test.app.services.SbFlowableService;

@SpringBootApplication(proxyBeanMethods = false)
public class SbFlowableProcessApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbFlowableProcessApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner init(final SbFlowableService SbFlowableService) {

	    return new CommandLineRunner() {
	        public void run(String... strings) throws Exception {
	        	SbFlowableService.createDemoUsers();
	        }
	    };
	}

}
