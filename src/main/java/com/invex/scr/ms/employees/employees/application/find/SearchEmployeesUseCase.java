package com.invex.scr.ms.employees.employees.application.find;

import com.invex.scr.ms.employees.employees.domain.model.Employee;
import java.util.List;

public interface SearchEmployeesUseCase {

  List<Employee> search(String name);
}
