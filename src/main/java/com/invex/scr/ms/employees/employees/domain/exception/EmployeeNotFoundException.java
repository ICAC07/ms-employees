package com.invex.scr.ms.employees.employees.domain.exception;

public class EmployeeNotFoundException extends RuntimeException {
  public EmployeeNotFoundException(long id) {
    super("No existe un empleado con id " + id);
  }
}
