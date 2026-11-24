package com.invex.scr.ms.employees.employees.application.find;

import com.invex.scr.ms.employees.employees.domain.model.EmployeePage;

public interface SearchEmployeesUseCase {

  EmployeePage search(String name, String cursor, Integer limit, boolean includeInactive);
}
