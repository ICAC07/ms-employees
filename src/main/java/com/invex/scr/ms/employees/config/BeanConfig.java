package com.invex.scr.ms.employees.config;

import com.invex.scr.ms.employees.employees.application.service.CursorCodec;
import com.invex.scr.ms.employees.employees.application.service.CursorPaginator;
import com.invex.scr.ms.employees.employees.application.service.PaginationPolicy;
import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class BeanConfig {

  @Bean
  Clock clock() {
    return Clock.systemUTC();
  }

  @Bean
  PaginationPolicy paginationPolicy(AppProperties properties) {
    return new PaginationPolicy(
        properties.pagination().defaultLimit(), properties.pagination().maxLimit());
  }

  @Bean
  CursorCodec cursorCodec() {
    return new CursorCodec();
  }

  @Bean
  CursorPaginator cursorPaginator(CursorCodec codec, PaginationPolicy policy) {
    return new CursorPaginator(codec, policy);
  }
}
