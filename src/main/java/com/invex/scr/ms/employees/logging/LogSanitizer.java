package com.invex.scr.ms.employees.logging;

import java.util.Locale;
import java.util.Set;

public final class LogSanitizer {

  static final String MASK = "***";

  static final int MAX_LENGTH = 200;

  private static final Set<String> SENSITIVE =
      Set.of("authorization", "proxy-authorization", "cookie", "set-cookie", "x-api-key");

  private LogSanitizer() {}

  public static String headerValue(String name, String value) {
    return SENSITIVE.contains(name.toLowerCase(Locale.ROOT)) ? MASK : clean(value);
  }

  public static String clean(String value) {
    String printable = value.replaceAll("\\p{Cntrl}", "_");
    return printable.length() > MAX_LENGTH ? printable.substring(0, MAX_LENGTH) : printable;
  }
}
