package com.invex.scr.ms.employees.employees.application.find;

import com.invex.scr.ms.employees.employees.application.service.CursorPaginator;
import com.invex.scr.ms.employees.employees.domain.exception.InvalidQueryException;
import com.invex.scr.ms.employees.employees.domain.model.EmployeePage;
import com.invex.scr.ms.employees.employees.domain.model.EmployeeRules;
import com.invex.scr.ms.employees.employees.infrastructure.adapter.out.persistence.EmployeeRepositoryPort;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchEmployeesHandler implements SearchEmployeesUseCase {

  private final EmployeeRepositoryPort repository;

  private final CursorPaginator paginator;

  public SearchEmployeesHandler(EmployeeRepositoryPort repository, CursorPaginator paginator) {
    this.repository = repository;
    this.paginator = paginator;
  }

  @Override
  @Transactional(readOnly = true)
  public EmployeePage search(String name, String cursor, Integer limit, boolean includeInactive) {
    String term =
        Optional.ofNullable(name)
            .map(String::strip)
            .filter(text -> !text.isEmpty())
            .orElseThrow(() -> new InvalidQueryException("El nombre a buscar es obligatorio"));
    if (term.length() > EmployeeRules.MAX_SEARCH_LENGTH) {
      throw new InvalidQueryException(
          "El nombre a buscar no puede superar " + EmployeeRules.MAX_SEARCH_LENGTH + " caracteres");
    }
    return paginator.paginate(
        cursor,
        limit,
        (afterId, maxResults) ->
            repository.searchAfter(term, afterId, maxResults, includeInactive));
  }
}
