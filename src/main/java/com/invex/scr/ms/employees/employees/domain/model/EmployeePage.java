package com.invex.scr.ms.employees.employees.domain.model;

import java.util.List;

public record EmployeePage(List<Employee> items, String nextCursor) {}
