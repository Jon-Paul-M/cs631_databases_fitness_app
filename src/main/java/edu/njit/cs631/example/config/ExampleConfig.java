package edu.njit.cs631.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan({"edu.njit.cs631.example.web.controller",
				"edu.njit.cs631.example.business.services"})
public class ExampleConfig {

}
