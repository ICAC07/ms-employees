package com.invex.scr.ms.employees.employees.application.find;

import com.invex.scr.ms.employees.employees.domain.exception.InvalidQueryException;
import com.invex.scr.ms.employees.employees.domain.model.Employee;
import com.invex.scr.ms.employees.employees.domain.model.EmployeeRules;
import com.invex.scr.ms.employees.employees.infrastructure.adapter.out.persistence.EmployeeRepositoryPort;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchEmployeesHandler implements SearchEmployeesUseCase {

  private final EmployeeRepositoryPort repository;

  public SearchEmployeesHandler(EmployeeRepositoryPort repository) {
    this.repository = repository;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Employee> search(String name) {
    String term =
        Optional.ofNullable(name)
            .map(String::strip)
            .filter(text -> !text.isEmpty())
            .orElseThrow(() -> new InvalidQueryException("El nombre a buscar es obligatorio"));
    if (term.length() > EmployeeRules.MAX_SEARCH_LENGTH) {
      throw new InvalidQueryException(
          "El nombre a buscar no puede superar " + EmployeeRules.MAX_SEARCH_LENGTH + " caracteres");
    }
    return repository.findByNameContaining(name);
  }
}
