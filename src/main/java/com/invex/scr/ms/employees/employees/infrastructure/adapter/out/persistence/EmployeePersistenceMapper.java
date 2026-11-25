package com.invex.scr.ms.employees.employees.infrastructure.adapter.out.persistence;

import com.invex.scr.ms.employees.employees.domain.model.Employee;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class EmployeePersistenceMapper {

  public EmployeeEntity toEntity(Employee employee) {
    return new EmployeeEntity(
        employee.id(),
        employee.firstName(),
        employee.middleName(),
        employee.paternalSurname(),
        employee.maternalSurname(),
        employee.gender(),
        employee.birthDate(),
        employee.jobTitle(),
        employee.createdAt(),
        employee.active());
  }

  public Employee toDomain(EmployeeEntity entity) {
    return new Employee(
        entity.getId(),
        entity.getFirstName(),
        entity.getMiddleName(),
        entity.getPaternalSurname(),
        entity.getMaternalSurname(),
        entity.getGender(),
        entity.getBirthDate(),
        entity.getJobTitle(),
        entity.getCreatedAt(),
        entity.isActive());
  }

  public List<Employee> toDomains(List<EmployeeEntity> entities) {
    return entities.stream()
        .map(entity -> {
          return new Employee(
              entity.getId(),
              entity.getFirstName(),
              entity.getMiddleName(),
              entity.getPaternalSurname(),
              entity.getMaternalSurname(),
              entity.getGender(),
              entity.getBirthDate(),
              entity.getJobTitle(),
              entity.getCreatedAt(),
              entity.isActive());
        })
        .toList();
  }
}
