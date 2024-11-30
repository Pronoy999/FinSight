package com.fin.sight.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.fin.sight.database", "com.fin.sight.api.*", "com.fin.sight.common"})
public class FinSightApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinSightApplication.class, args);
        log.info("FinSight Application Started");
    }
}
