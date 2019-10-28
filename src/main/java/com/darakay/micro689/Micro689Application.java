package com.darakay.micro689;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Micro689Application {

    public static void main(String[] args) {
        SpringApplication.run(Micro689Application.class, args);
    }

}
