package com.invex.scr.ms.employees.employees.application.service;

import com.invex.scr.ms.employees.employees.domain.model.Employee;
import com.invex.scr.ms.employees.employees.domain.model.EmployeePage;
import java.util.List;

public class CursorPaginator {

  private final CursorCodec codec;

  private final PaginationPolicy policy;

  public CursorPaginator(CursorCodec codec, PaginationPolicy policy) {
    this.codec = codec;
    this.policy = policy;
  }

  @FunctionalInterface
  public interface PageFetcher {
    List<Employee> fetch(long afterId, int maxResults);
  }

  public EmployeePage paginate(String cursor, Integer limit, PageFetcher fetcher) {
    int size = policy.resolve(limit);
    long afterId = codec.decode(cursor);
    List<Employee> rows = fetcher.fetch(afterId, size + 1);
    boolean hasMore = rows.size() > size;
    List<Employee> items = hasMore ? rows.subList(0, size) : rows;
    String next = hasMore ? codec.encode(items.get(size - 1).id()) : null;
    return new EmployeePage(List.copyOf(items), next);
  }
}
