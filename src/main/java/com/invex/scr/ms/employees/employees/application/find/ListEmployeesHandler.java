package com.invex.scr.ms.employees.employees.application.find;

import com.invex.scr.ms.employees.employees.domain.model.Employee;
import com.invex.scr.ms.employees.employees.infrastructure.adapter.out.persistence.EmployeeRepositoryPort;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ListEmployeesHandler implements ListEmployeesUseCase {

  private final EmployeeRepositoryPort repository;

  public ListEmployeesHandler(EmployeeRepositoryPort repository) {
    this.repository = repository;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Employee> list() {
    return repository.findAll();
  }
}
