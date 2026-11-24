package com.invex.scr.ms.employees.employees.application.create;

import com.invex.scr.ms.employees.employees.domain.model.Employee;
import com.invex.scr.ms.employees.employees.domain.model.NewEmployee;
import java.util.List;

public interface CreateEmployeesUseCase {

  List<Employee> create(List<NewEmployee> employees);
}
