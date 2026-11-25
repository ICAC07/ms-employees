package com.invex.scr.ms.employees.employees.infrastructure.adapter.out.persistence;

import com.invex.scr.ms.employees.employees.domain.model.Employee;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeePersistenceAdapter implements EmployeeRepositoryPort {

  private static final char ESCAPE = '!';
  private final EmployeeJpaRepository repository;
  private final EmployeePersistenceMapper mapper;

  EmployeePersistenceAdapter(EmployeeJpaRepository repository, EmployeePersistenceMapper mapper) {
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

  @Override
  public List<Employee> findAfter(long afterId, int maxResults, boolean includeInactive) {
    return repository.findPage(afterId, includeInactive, PageRequest.ofSize(maxResults)).stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public List<Employee> searchAfter(
      String term, long afterId, int maxResults, boolean includeInactive) {
    return repository
        .searchPage(toLikePattern(term), afterId, includeInactive, PageRequest.ofSize(maxResults))
        .stream()
        .map(mapper::toDomain)
        .toList();
  }

  static String toLikePattern(String term) {
    String escaped =
        term.toLowerCase(Locale.ROOT)
            .replace(String.valueOf(ESCAPE), "" + ESCAPE + ESCAPE)
            .replace("%", "" + ESCAPE + "%")
            .replace("_", "" + ESCAPE + "_");
    return "%" + escaped + "%";
  }
}
