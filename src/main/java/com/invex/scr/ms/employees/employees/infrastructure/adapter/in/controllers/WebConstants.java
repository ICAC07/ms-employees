package com.invex.scr.ms.employees.employees.infrastructure.adapter.in.controllers;

public final class WebConstants {

  public static final String EMPLOYEES_PATH = "/employees";
  public static final String SEARCH_PATH = "/search";
  public static final String ID_PATH = "/{id}";
  public static final String DATE_PATTERN = "dd-MM-uuuu";
  public static final String ERRORS_PROPERTY = "errors";
  public static final String INTERNAL_ERROR_DETAIL =
      "Ocurrió un error inesperado. Intente de nuevo más tarde.";

  public static final String VALIDATION_TITLE = "Solicitud inválida";
  public static final String VALIDATION_DETAIL = "Uno o más campos no son válidos";
  public static final String UNREADABLE_DETAIL =
      "El cuerpo no es un JSON válido o contiene valores no admitidos "
          + "(fechas en formato dd-MM-yyyy, sexo MALE, FEMALE u OTHER)";

  public static final String NOT_FOUND_TITLE = "Empleado no encontrado";

  public static final String INVALID_TITLE = "Datos inválidos";
  public static final String INTERNAL_TITLE = "Error interno";

  private WebConstants() {}
}
