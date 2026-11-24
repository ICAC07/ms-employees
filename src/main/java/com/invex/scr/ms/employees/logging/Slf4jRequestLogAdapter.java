package com.invex.scr.ms.employees.logging;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Slf4jRequestLogAdapter implements RequestLogPort {

  private static final Logger LOG = LoggerFactory.getLogger("http.requests");

  @Override
  public void record(
      String method, String path, int status, long millis, Map<String, String> headers) {
    LOG.info("{} {} -> {} ({} ms) headers={}", method, path, status, millis, headers);
  }
}
