package edu.mipt.ponomarev.leadiq;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LeadiqApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeadiqApplication.class, args);
	}

	@Bean
	public Gson gson() {
		return new Gson();
	}
}
