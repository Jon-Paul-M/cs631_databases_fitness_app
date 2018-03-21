package edu.njit.cs631.medical.testutils;

import edu.njit.cs631.medical.config.MedicalConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Primary
public class TestDbConfig extends MedicalConfig {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Override
    public DataSource dataSource() {
        EmbeddedDatabase datasource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        return datasource;
    }

    @Override
    protected Properties additionalProperties() {
        Properties properties = super.additionalProperties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        return properties;
    }
}
