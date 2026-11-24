package com.invex.scr.ms.employees.security;

import com.invex.scr.ms.employees.logging.LogSanitizer;
import com.invex.scr.ms.employees.logging.RequestLogPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ContextFilter extends OncePerRequestFilter {

  public static final String CORRELATION_HEADER = "X-Correlation-Id";

  static final String MDC_KEY = "correlationId";

  private static final Pattern VALID_ID = Pattern.compile("[A-Za-z0-9-]{1,64}");

  private final RequestLogPort log;

  public ContextFilter(RequestLogPort log) {
    this.log = log;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    String correlationId = correlationId(request.getHeader(CORRELATION_HEADER));
    MDC.put(MDC_KEY, correlationId);
    response.setHeader(CORRELATION_HEADER, correlationId);
    long start = System.nanoTime();
    try {
      chain.doFilter(request, response);
    } finally {
      long millis = (System.nanoTime() - start) / 1_000_000;
      log.record(
          request.getMethod(),
          LogSanitizer.clean(request.getRequestURI()),
          response.getStatus(),
          millis,
          headers(request));
      MDC.remove(MDC_KEY);
    }
  }

  private static String correlationId(String incoming) {
    return incoming != null && VALID_ID.matcher(incoming).matches()
        ? incoming
        : UUID.randomUUID().toString();
  }

  private static Map<String, String> headers(HttpServletRequest request) {
    Map<String, String> result = new LinkedHashMap<>();
    Collections.list(request.getHeaderNames())
        .forEach(name -> result.put(name, LogSanitizer.headerValue(name, request.getHeader(name))));
    return result;
  }
}
