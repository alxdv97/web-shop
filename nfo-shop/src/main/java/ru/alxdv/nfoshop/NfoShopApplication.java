package ru.alxdv.nfoshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NfoShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(NfoShopApplication.class, args);
    }
}
