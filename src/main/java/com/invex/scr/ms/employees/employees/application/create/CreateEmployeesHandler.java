package com.invex.scr.ms.employees.employees.application.create;

import com.invex.scr.ms.employees.employees.domain.exception.InvalidEmployeeException;
import com.invex.scr.ms.employees.employees.domain.model.Employee;
import com.invex.scr.ms.employees.employees.domain.model.EmployeeRules;
import com.invex.scr.ms.employees.employees.domain.model.NewEmployee;
import com.invex.scr.ms.employees.employees.infrastructure.adapter.out.persistence.EmployeeRepositoryPort;
import java.time.Clock;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateEmployeesHandler implements CreateEmployeesUseCase {

  private final EmployeeRepositoryPort repository;

  private final Clock clock;

  public CreateEmployeesHandler(EmployeeRepositoryPort repository, Clock clock) {
    this.repository = repository;
    this.clock = clock;
  }

  @Override
  @Transactional
  public List<Employee> create(List<NewEmployee> employees) {
    if (employees == null || employees.isEmpty()) {
      throw new InvalidEmployeeException("Debe enviarse al menos un empleado");
    }
    if (employees.size() > EmployeeRules.MAX_BATCH_SIZE) {
      throw new InvalidEmployeeException(
          "No se pueden crear más de " + EmployeeRules.MAX_BATCH_SIZE + " empleados por petición");
    }
    List<Employee> toSave = employees.stream().map(data -> Employee.register(data, clock)).toList();
    return repository.saveAll(toSave);
  }
}
