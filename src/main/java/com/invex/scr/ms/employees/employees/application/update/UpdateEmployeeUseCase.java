package com.invex.scr.ms.employees.employees.application.update;

import com.invex.scr.ms.employees.employees.domain.model.Employee;
import com.invex.scr.ms.employees.employees.domain.model.EmployeeChanges;

public interface UpdateEmployeeUseCase {
  Employee update(long id, EmployeeChanges changes);
}
