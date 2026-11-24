package com.invex.scr.ms.employees.employees.infrastructure.adapter.out.persistence;

import com.invex.scr.ms.employees.employees.domain.model.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepositoryPort {

  Employee save(Employee employee);

  List<Employee> saveAll(List<Employee> employees);

  Optional<Employee> findById(long id);

  List<Employee> findAfter(long afterId, int maxResults, boolean includeInactive);

  List<Employee> searchAfter(String term, long afterId, int maxResults, boolean includeInactive);
}
