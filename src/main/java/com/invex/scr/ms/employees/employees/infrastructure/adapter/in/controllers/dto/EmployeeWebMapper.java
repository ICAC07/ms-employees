package com.invex.scr.ms.employees.employees.infrastructure.adapter.in.controllers.dto;

import com.invex.scr.ms.employees.employees.domain.model.Employee;
import com.invex.scr.ms.employees.employees.domain.model.EmployeeChanges;
import com.invex.scr.ms.employees.employees.domain.model.NewEmployee;
import java.time.Clock;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeWebMapper {
  private final Clock clock;

  public NewEmployee toNewEmployee(CreateEmployeeRequest request) {
    return new NewEmployee(
        request.firstName(),
        request.middleName(),
        request.paternalSurname(),
        request.maternalSurname(),
        request.age(),
        request.gender(),
        request.birthDate(),
        request.jobTitle(),
        request.active());
  }

  public EmployeeChanges toChanges(UpdateEmployeeRequest request) {
    return new EmployeeChanges(
        request.firstName(),
        request.middleName(),
        request.paternalSurname(),
        request.maternalSurname(),
        request.age(),
        request.gender(),
        request.birthDate(),
        request.jobTitle(),
        request.active());
  }

  public EmployeeResponse toResponse(Employee employee) {
    return new EmployeeResponse(
        employee.id(),
        employee.firstName(),
        employee.middleName(),
        employee.paternalSurname(),
        employee.maternalSurname(),
        employee.age(clock),
        employee.gender(),
        employee.birthDate(),
        employee.jobTitle(),
        employee.createdAt(),
        employee.active());
  }

  /*
  public EmployeePageResponse toResponse(List<EmployeePage> page) {
    return new EmployeePageResponse(
        page.items().stream().map(this::toResponse).toList(), page.nextCursor());
  }
  */
  public EmployeeListResponse toResponse(List<Employee> employees) {
    return new EmployeeListResponse(
        employees.stream()
            .map(employee ->
                new EmployeeResponse(
                    employee.id(),
                    employee.firstName(),
                    employee.middleName(),
                    employee.paternalSurname(),
                    employee.maternalSurname(),
                    employee.age(clock),
                    employee.gender(),
                    employee.birthDate(),
                    employee.jobTitle(),
                    employee.createdAt(),
                    employee.active()))
            .toList());
  }
}
