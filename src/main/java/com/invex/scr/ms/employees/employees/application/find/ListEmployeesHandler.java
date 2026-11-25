package com.invex.scr.ms.employees.employees.application.find;

import com.invex.scr.ms.employees.employees.domain.model.Employee;
import com.invex.scr.ms.employees.employees.infrastructure.adapter.out.persistence.EmployeeRepositoryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ListEmployeesHandler implements ListEmployeesUseCase {
  private final EmployeeRepositoryPort repository;

  @Override
  @Transactional(readOnly = true)
  public List<Employee> list() {
    return repository.findAll();
  }
}
