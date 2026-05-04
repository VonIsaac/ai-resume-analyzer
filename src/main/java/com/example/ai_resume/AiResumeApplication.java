package com.example.ai_resume;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AiResumeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiResumeApplication.class, args);
	}

	// This runs ONCE when the app starts — like a startup script
	@Bean
	public CommandLineRunner testRun() {
		return args -> {
			System.out.println("=========================================");
			System.out.println("🚀 Hello from Spring Boot!");
			System.out.println("✅ AI Resume App is running successfully!");
			System.out.println("🌐 Open: http://localhost:8080");
			System.out.println("=========================================");
		};
	}

}
