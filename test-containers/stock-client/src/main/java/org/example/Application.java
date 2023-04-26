package org.example;

import org.example.client.StockClient;
import org.example.dao.UserDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public StockClient stockClient() {
        return new StockClient("http://localhost:8080", new RestTemplate());
    }

    @Bean
    public UserDao userDao() {
        return new UserDao(new HashMap<>());
    }
}
