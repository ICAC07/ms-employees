package com.invex.scr.ms.employees.config;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class BeanConfig {

  @Bean
  Clock clock() {
    return Clock.systemUTC();
  }
}
