package com.invex.scr.ms.employees.employees.domain.model;

import com.invex.scr.ms.employees.employees.domain.exception.InvalidEmployeeException;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

public record Employee(
    Long id,
    String firstName,
    String middleName,
    String paternalSurname,
    String maternalSurname,
    Gender gender,
    LocalDate birthDate,
    String jobTitle,
    Instant createdAt,
    boolean active) {

  public Employee {
    firstName = required(firstName, "firstName");
    middleName = normalize(middleName);
    paternalSurname = required(paternalSurname, "paternalSurname");
    maternalSurname = normalize(maternalSurname);
    jobTitle = required(jobTitle, "jobTitle");
    gender = requiredValue(gender, "gender");
    birthDate = requiredValue(birthDate, "birthDate");
    createdAt = requiredValue(createdAt, "createdAt");
  }

  public static Employee register(NewEmployee data, Clock clock) {
    Employee employee =
        new Employee(
            null,
            data.firstName(),
            data.middleName(),
            data.paternalSurname(),
            data.maternalSurname(),
            data.gender(),
            data.birthDate(),
            data.jobTitle(),
            clock.instant(),
            Optional.ofNullable(data.active()).orElse(true));
    return employee.validated(data.age(), clock);
  }

  public Employee apply(EmployeeChanges changes, Clock clock) {
    Employee updated =
        new Employee(
            id,
            keep(changes.firstName(), firstName),
            keep(changes.middleName(), middleName),
            keep(changes.paternalSurname(), paternalSurname),
            keep(changes.maternalSurname(), maternalSurname),
            keep(changes.gender(), gender),
            keep(changes.birthDate(), birthDate),
            keep(changes.jobTitle(), jobTitle),
            createdAt,
            keep(changes.active(), active));
    return updated.validated(changes.age(), clock);
  }

  public Employee deactivate() {
    return new Employee(
        id,
        firstName,
        middleName,
        paternalSurname,
        maternalSurname,
        gender,
        birthDate,
        jobTitle,
        createdAt,
        false);
  }

  public int age(Clock clock) {
    return Period.between(birthDate, LocalDate.now(clock)).getYears();
  }

  private Employee validated(Integer declaredAge, Clock clock) {
    if (!birthDate.isBefore(LocalDate.now(clock))) {
      throw new InvalidEmployeeException("La fecha de nacimiento debe ser anterior a hoy");
    }
    int computed = age(clock);
    if (computed > EmployeeRules.MAX_AGE) {
      throw new InvalidEmployeeException("La edad calculada supera el máximo permitido");
    }
    if (declaredAge != null && declaredAge != computed) {
      throw new InvalidEmployeeException("La edad indicada no coincide con la fecha de nacimiento");
    }
    return this;
  }

  private static <T> T keep(T candidate, T current) {
    return Optional.ofNullable(candidate).orElse(current);
  }

  private static String normalize(String value) {
    return Optional.ofNullable(value)
        .map(String::strip)
        .filter(text -> !text.isEmpty())
        .orElse(null);
  }

  private static String required(String value, String field) {
    return Optional.ofNullable(normalize(value))
        .orElseThrow(() -> new InvalidEmployeeException("El campo " + field + " es obligatorio"));
  }

  private static <T> T requiredValue(T value, String field) {
    return Optional.ofNullable(value)
        .orElseThrow(() -> new InvalidEmployeeException("El campo " + field + " es obligatorio"));
  }
}
