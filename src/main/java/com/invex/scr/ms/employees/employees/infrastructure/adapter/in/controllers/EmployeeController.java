package com.invex.scr.ms.employees.employees.infrastructure.adapter.in.controllers;

import com.invex.scr.ms.employees.employees.application.create.CreateEmployeesUseCase;
import com.invex.scr.ms.employees.employees.application.delete.DeleteEmployeeUseCase;
import com.invex.scr.ms.employees.employees.application.find.GetEmployeeUseCase;
import com.invex.scr.ms.employees.employees.application.find.ListEmployeesUseCase;
import com.invex.scr.ms.employees.employees.application.find.SearchEmployeesUseCase;
import com.invex.scr.ms.employees.employees.application.update.UpdateEmployeeUseCase;
import com.invex.scr.ms.employees.employees.domain.model.EmployeeRules;
import com.invex.scr.ms.employees.employees.infrastructure.adapter.in.controllers.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(WebConstants.EMPLOYEES_PATH)
public class EmployeeController {

  private final CreateEmployeesUseCase createEmployees;
  private final UpdateEmployeeUseCase updateEmployee;
  private final DeleteEmployeeUseCase deleteEmployee;
  private final GetEmployeeUseCase getEmployee;
  private final ListEmployeesUseCase listEmployees;
  private final SearchEmployeesUseCase searchEmployees;
  private final EmployeeWebMapper mapper;

  public EmployeeController(
      CreateEmployeesUseCase createEmployees,
      UpdateEmployeeUseCase updateEmployee,
      DeleteEmployeeUseCase deleteEmployee,
      GetEmployeeUseCase getEmployee,
      ListEmployeesUseCase listEmployees,
      SearchEmployeesUseCase searchEmployees,
      EmployeeWebMapper mapper) {
    this.createEmployees = createEmployees;
    this.updateEmployee = updateEmployee;
    this.deleteEmployee = deleteEmployee;
    this.getEmployee = getEmployee;
    this.listEmployees = listEmployees;
    this.searchEmployees = searchEmployees;
    this.mapper = mapper;
  }

  /**
   * {@code GET /employees}: lists employees page by page.
   *
   * @param cursor opaque cursor from a previous page, optional
   * @param limit page size, optional
   * @param includeInactive whether logically deleted employees are included, {@code false} by
   *     default
   * @return one page of employees
   */
  @GetMapping
  @Operation(summary = "Lista empleados con paginación por cursor (solo activos por defecto)")
  public EmployeePageResponse list(
      @RequestParam(required = false) String cursor,
      @RequestParam(required = false) Integer limit,
      @RequestParam(defaultValue = "false") boolean includeInactive) {
    return mapper.toResponse(listEmployees.list(cursor, limit, includeInactive));
  }

  /**
   * {@code GET /employees/search}: partial, case-insensitive search by name.
   *
   * @param name text to look for, not blank, at most 100 characters
   * @param cursor opaque cursor from a previous page, optional
   * @param limit page size, optional
   * @param includeInactive whether logically deleted employees are included, {@code false} by
   *     default
   * @return one page of matches
   */
  @GetMapping(WebConstants.SEARCH_PATH)
  @Operation(summary = "Busca por coincidencia parcial en nombres y apellidos")
  public EmployeePageResponse search(
      @RequestParam @NotBlank @Size(max = EmployeeRules.MAX_SEARCH_LENGTH) String name,
      @RequestParam(required = false) String cursor,
      @RequestParam(required = false) Integer limit,
      @RequestParam(defaultValue = "false") boolean includeInactive) {
    return mapper.toResponse(searchEmployees.search(name, cursor, limit, includeInactive));
  }

  /**
   * {@code GET /employees/{id}}: returns one employee.
   *
   * @param id employee identifier
   * @return the employee
   */
  @GetMapping(WebConstants.ID_PATH)
  @Operation(summary = "Detalle de un empleado")
  public EmployeeResponse get(@PathVariable long id) {
    return mapper.toResponse(getEmployee.get(id));
  }

  /**
   * {@code POST /employees}: registers one or several employees atomically.
   *
   * @param requests between 1 and 100 valid employees
   * @return the created employees, answered with HTTP 201
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Crea uno o varios empleados en una sola operación")
  public List<EmployeeResponse> create(
      @RequestBody @NotEmpty @Size(max = EmployeeRules.MAX_BATCH_SIZE)
          List<@NotNull @Valid CreateEmployeeRequest> requests) {
    return createEmployees.create(requests.stream().map(mapper::toNewEmployee).toList()).stream()
        .map(mapper::toResponse)
        .toList();
  }

  /**
   * {@code PUT /employees/{id}}: updates all or some fields of an employee.
   *
   * @param id employee identifier
   * @param request the fields to change
   * @return the updated employee
   */
  @PutMapping(WebConstants.ID_PATH)
  @Operation(summary = "Actualiza todos o algunos campos de un empleado")
  public EmployeeResponse update(
      @PathVariable long id, @RequestBody @Valid UpdateEmployeeRequest request) {
    return mapper.toResponse(updateEmployee.update(id, mapper.toChanges(request)));
  }

  /**
   * {@code DELETE /employees/{id}}: logically deletes an employee, answered with HTTP 204.
   *
   * @param id employee identifier
   */
  @DeleteMapping(WebConstants.ID_PATH)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Baja lógica: Inactiva al empleado")
  public void delete(@PathVariable long id) {
    deleteEmployee.delete(id);
  }
}
