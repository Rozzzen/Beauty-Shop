package com.zhuk.beautyshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:config.xml")
public class BeautyShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeautyShopApplication.class, args);
    }

}
