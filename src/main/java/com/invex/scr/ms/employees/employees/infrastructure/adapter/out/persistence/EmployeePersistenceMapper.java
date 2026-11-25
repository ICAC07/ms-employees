package com.invex.scr.ms.employees.employees.infrastructure.adapter.out.persistence;

import com.invex.scr.ms.employees.employees.domain.model.Employee;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeePersistenceMapper {

  EmployeeEntity toEntity(Employee employee);

  Employee toDomain(EmployeeEntity entity);

  List<Employee> toDomains(List<EmployeeEntity> entities);
}
