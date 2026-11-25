package com.invex.scr.ms.employees.logging;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LogSanitizerTest {

  @Test
  void masksSensitiveHeadersCaseInsensitively() {
    for (String name :
        new String[] {
          "Authorization", "COOKIE", "set-cookie", "X-Api-Key", "Proxy-Authorization"
        }) {
      assertThat(LogSanitizer.headerValue(name, "secret")).isEqualTo(LogSanitizer.MASK);
    }
  }

  @Test
  void keepsHarmlessHeaders() {
    assertThat(LogSanitizer.headerValue("Accept", "application/json"))
        .isEqualTo("application/json");
  }

  @Test
  void neutralisesControlCharactersToPreventLogInjection() {
    assertThat(LogSanitizer.clean("a\r\nFAKE entry\tb")).isEqualTo("a__FAKE entry_b");
  }

  @Test
  void truncatesLongValues() {
    assertThat(LogSanitizer.clean("x".repeat(500))).hasSize(LogSanitizer.MAX_LENGTH);
    assertThat(LogSanitizer.clean("x".repeat(LogSanitizer.MAX_LENGTH)))
        .hasSize(LogSanitizer.MAX_LENGTH);
  }
}
