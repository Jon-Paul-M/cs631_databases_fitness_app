package edu.njit.cs631.fitness.config;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class H2Configuration {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	private String h2TcpPort = "8043";

	@Bean
	public Server getH2WebServer() throws SQLException {
		Server server = Server.createWebServer("-web","-webAllowOthers", "-webPort","8082");
		server.start();
		logger.info("getH2WebServer: " + server.getStatus());
		return server;
	}
	
	@Bean
	public Server getH2TcpServer() throws SQLException {
		Server server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", h2TcpPort);
		server.start();
		logger.info("getH2TcpServer: " + server.getStatus());
		return server;
	}
}
