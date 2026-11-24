package com.invex.scr.ms.employees.employees.domain.model;

import java.time.LocalDate;

public record EmployeeChanges(
    String firstName,
    String middleName,
    String paternalSurname,
    String maternalSurname,
    Integer age,
    Gender gender,
    LocalDate birthDate,
    String jobTitle,
    Boolean active) {}
