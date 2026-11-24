package com.invex.scr.ms.employees.employees.application.find;

import com.invex.scr.ms.employees.employees.application.service.CursorPaginator;
import com.invex.scr.ms.employees.employees.domain.model.EmployeePage;
import com.invex.scr.ms.employees.employees.infrastructure.adapter.out.persistence.EmployeeRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ListEmployeesHandler implements ListEmployeesUseCase {

  private final EmployeeRepositoryPort repository;

  private final CursorPaginator paginator;

  public ListEmployeesHandler(EmployeeRepositoryPort repository, CursorPaginator paginator) {
    this.repository = repository;
    this.paginator = paginator;
  }

  @Override
  @Transactional(readOnly = true)
  public EmployeePage list(String cursor, Integer limit, boolean includeInactive) {
    return paginator.paginate(
        cursor,
        limit,
        (afterId, maxResults) -> repository.findAfter(afterId, maxResults, includeInactive));
  }
}
