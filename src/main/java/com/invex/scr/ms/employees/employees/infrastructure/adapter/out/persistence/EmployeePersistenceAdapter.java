package com.invex.scr.ms.employees.employees.infrastructure.adapter.out.persistence;

import com.invex.scr.ms.employees.employees.domain.model.Employee;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeePersistenceAdapter implements EmployeeRepositoryPort {

  private final EmployeeJpaRepository repository;
  private final EmployeePersistenceMapper mapper;

  public EmployeePersistenceAdapter(
      EmployeeJpaRepository repository, EmployeePersistenceMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public Employee save(Employee employee) {
    return mapper.toDomain(repository.save(mapper.toEntity(employee)));
  }

  @Override
  public List<Employee> saveAll(List<Employee> employees) {
    return repository.saveAll(employees.stream().map(mapper::toEntity).toList()).stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public Optional<Employee> findById(long id) {
    return repository.findById(id).map(mapper::toDomain);
  }

  @Override
  public List<Employee> findAll() {
    return mapper.toDomains(repository.findAll());
  }

  @Override
  public List<Employee> findByNameContaining(String firstName) {
    return mapper.toDomains(repository.findByFirstNameContains(firstName));
  }
}
