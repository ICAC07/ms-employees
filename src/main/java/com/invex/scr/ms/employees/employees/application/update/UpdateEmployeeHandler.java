package com.invex.scr.ms.employees.employees.application.update;

import com.invex.scr.ms.employees.employees.domain.exception.EmployeeNotFoundException;
import com.invex.scr.ms.employees.employees.domain.model.Employee;
import com.invex.scr.ms.employees.employees.domain.model.EmployeeChanges;
import com.invex.scr.ms.employees.employees.infrastructure.adapter.out.persistence.EmployeeRepositoryPort;
import java.time.Clock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateEmployeeHandler implements UpdateEmployeeUseCase {
  private final EmployeeRepositoryPort repository;
  private final Clock clock;

  public UpdateEmployeeHandler(EmployeeRepositoryPort repository, Clock clock) {
    this.repository = repository;
    this.clock = clock;
  }

  @Override
  @Transactional
  public Employee update(long id, EmployeeChanges changes) {
    return repository
        .findById(id)
        .map(current -> repository.save(current.apply(changes, clock)))
        .orElseThrow(() -> new EmployeeNotFoundException(id));
  }
}
