package com.invex.scr.ms.employees.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.invex.scr.ms.employees.logging.Slf4jRequestLogAdapter;
import jakarta.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class ContextFilterTest {

  record Entry(String method, String path, int status, Map<String, String> headers) {}

  private final List<Entry> entries = new ArrayList<>();
  private final ContextFilter filter =
      new ContextFilter((method, path, status, millis, headers) ->
          entries.add(new Entry(method, path, status, headers)));

  @Test
  void logsRequestWithMaskedHeadersAndKeepsAValidCorrelationId() throws Exception {
    MockHttpServletRequest request = new MockHttpServletRequest("GET", "/employees");
    request.addHeader("Authorization", "Basic c2VjcmV0");
    request.addHeader("Cookie", "SESSION=abc");
    request.addHeader("Accept", "application/json");
    request.addHeader(ContextFilter.CORRELATION_HEADER, "abc-123");
    MockHttpServletResponse response = new MockHttpServletResponse();
    response.setStatus(200);

    filter.doFilter(request, response, new MockFilterChain());

    assertThat(response.getHeader(ContextFilter.CORRELATION_HEADER)).isEqualTo("abc-123");
    assertThat(entries).hasSize(1);
    Entry entry = entries.get(0);
    assertThat(entry.method()).isEqualTo("GET");
    assertThat(entry.path()).isEqualTo("/employees");
    assertThat(entry.status()).isEqualTo(200);
    assertThat(entry.headers())
        .containsEntry("Authorization", "***")
        .containsEntry("Cookie", "***")
        .containsEntry("Accept", "application/json");
    assertThat(MDC.get(ContextFilter.MDC_KEY)).isNull();
  }

  @Test
  void replacesMissingOrSuspiciousCorrelationIds() throws Exception {
    for (String incoming : new String[] {null, "bad id\r\nforged", "x".repeat(65)}) {
      MockHttpServletRequest request = new MockHttpServletRequest("GET", "/x");
      if (incoming != null) {
        request.addHeader(ContextFilter.CORRELATION_HEADER, incoming);
      }
      MockHttpServletResponse response = new MockHttpServletResponse();

      filter.doFilter(request, response, new MockFilterChain());

      String generated = response.getHeader(ContextFilter.CORRELATION_HEADER);
      assertThat(generated).isNotEqualTo(incoming).matches("[0-9a-f-]{36}");
    }
  }

  @Test
  void stillLogsAndCleansUpWhenTheChainFails() {
    MockHttpServletRequest request = new MockHttpServletRequest("POST", "/boom");
    MockHttpServletResponse response = new MockHttpServletResponse();

    assertThatThrownBy(() ->
            filter.doFilter(
                request,
                response,
                (req, res) -> {
                  throw new ServletException("fallo");
                }))
        .isInstanceOf(ServletException.class);

    assertThat(entries).hasSize(1);
    assertThat(MDC.get(ContextFilter.MDC_KEY)).isNull();
  }

  @Test
  void slf4jAdapterAcceptsEntries() {
    new Slf4jRequestLogAdapter().record("GET", "/x", 200, 1L, Map.of("Accept", "*/*"));
  }
}
