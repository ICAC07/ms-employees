package com.invex.scr.ms.employees.employees.application.find;

import com.invex.scr.ms.employees.employees.domain.exception.EmployeeNotFoundException;
import com.invex.scr.ms.employees.employees.domain.model.Employee;
import com.invex.scr.ms.employees.employees.infrastructure.adapter.out.persistence.EmployeeRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetEmployeeHandler implements GetEmployeeUseCase {

  private final EmployeeRepositoryPort repository;

  public GetEmployeeHandler(EmployeeRepositoryPort repository) {
    this.repository = repository;
  }

  @Override
  @Transactional(readOnly = true)
  public Employee get(long id) {
    return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
  }
}
