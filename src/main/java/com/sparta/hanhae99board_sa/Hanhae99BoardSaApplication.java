package com.sparta.hanhae99board_sa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Hanhae99BoardSaApplication {

    public static void main(String[] args) {
        SpringApplication.run(Hanhae99BoardSaApplication.class, args);
    }

}
