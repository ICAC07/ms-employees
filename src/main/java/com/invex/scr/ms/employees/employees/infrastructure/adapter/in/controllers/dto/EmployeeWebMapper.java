package com.invex.scr.ms.employees.employees.infrastructure.adapter.in.controllers.dto;

import com.invex.scr.ms.employees.employees.domain.model.Employee;
import com.invex.scr.ms.employees.employees.domain.model.EmployeeChanges;
import com.invex.scr.ms.employees.employees.domain.model.NewEmployee;
import java.time.Clock;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = Clock.class)
public interface EmployeeWebMapper {

  NewEmployee toNewEmployee(CreateEmployeeRequest request);

  EmployeeChanges toChanges(UpdateEmployeeRequest request);

  EmployeeResponse toResponse(Employee employee);

  List<EmployeeResponse> toResponse(List<Employee> employees);
}
