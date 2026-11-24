package com.invex.scr.ms.employees.employees.application.find;

import com.invex.scr.ms.employees.employees.domain.model.Employee;

public interface GetEmployeeUseCase {
  Employee get(long id);
}
