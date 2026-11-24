package com.invex.scr.ms.employees;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.invex.scr")
@EntityScan(basePackages = "com.invex.scr")
@EnableJpaRepositories(basePackages = "com.invex.scr")
@ConfigurationPropertiesScan
public class MsEmployeesApplication {
  public static void main(String[] args) {
    SpringApplication.run(MsEmployeesApplication.class, args);
  }
}
