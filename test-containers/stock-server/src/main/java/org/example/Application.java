package org.example;

import org.example.dao.StockDao;
import org.example.model.Stock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public StockDao stockDao() {
        return new StockDao(
                new HashMap<>(
                        Map.of(
                                "two", new Stock("two", 20, 20000),
                                "three", new Stock("three", 30, 30000)
                        )
                )
        );
    }
}
