package com.invex.scr.ms.employees.employees.application.delete;

import com.invex.scr.ms.employees.employees.domain.exception.EmployeeNotFoundException;
import com.invex.scr.ms.employees.employees.domain.model.Employee;
import com.invex.scr.ms.employees.employees.infrastructure.adapter.out.persistence.EmployeeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteEmployeeHandler implements DeleteEmployeeUseCase {
  private final EmployeeRepositoryPort repository;

  @Override
  @Transactional
  public void delete(long id) {
    Employee current = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    if (current.active()) {
      repository.save(current.deactivate());
    }
  }
}
