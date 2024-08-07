package com.project.bff;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    // The database connection settings are in the properties
    //
    // @Bean
    // DataSource dataSource() {

    // DriverManagerDataSource dataSource = new DriverManagerDataSource();

    // dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    // dataSource.setUrl("jdbc:mysql://localhost:3306/bffdb");
    // dataSource.setUsername("root");
    // dataSource.setPassword("pwdmysql");

    // return dataSource;
    // }

    @Bean
    RestTemplate restTemplate() {

        return new RestTemplate();
    }
}