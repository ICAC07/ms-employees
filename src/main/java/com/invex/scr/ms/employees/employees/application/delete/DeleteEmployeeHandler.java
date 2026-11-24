package com.invex.scr.ms.employees.employees.application.delete;

import com.invex.scr.ms.employees.employees.domain.exception.EmployeeNotFoundException;
import com.invex.scr.ms.employees.employees.domain.model.Employee;
import com.invex.scr.ms.employees.employees.infrastructure.adapter.out.persistence.EmployeeRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteEmployeeHandler implements DeleteEmployeeUseCase {

  private final EmployeeRepositoryPort repository;

  public DeleteEmployeeHandler(EmployeeRepositoryPort repository) {
    this.repository = repository;
  }

  @Override
  @Transactional
  public void delete(long id) {
    Employee current = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    if (current.active()) {
      repository.save(current.deactivate());
    }
  }
}
