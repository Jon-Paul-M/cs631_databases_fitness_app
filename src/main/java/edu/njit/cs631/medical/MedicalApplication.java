package edu.njit.cs631.medical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"edu.njit.cs631.medical.config",
	            "edu.njit.cs631.example.config"})
public class MedicalApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicalApplication.class, args);
	}
}
