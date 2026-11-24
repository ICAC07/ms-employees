package com.invex.scr.ms.employees.logging;

import java.util.Map;

public interface RequestLogPort {
  void record(String method, String path, int status, long millis, Map<String, String> headers);
}
