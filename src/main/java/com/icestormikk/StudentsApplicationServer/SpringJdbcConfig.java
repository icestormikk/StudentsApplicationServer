package com.icestormikk.StudentsApplicationServer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Настройки соединения с базой данных
 */
@Configuration
public class SpringJdbcConfig {
    @Bean
    public DataSource myPostgresDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:15432/students_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("root");

        return dataSource;
    }
}
