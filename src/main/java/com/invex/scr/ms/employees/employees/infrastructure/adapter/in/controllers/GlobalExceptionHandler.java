package com.invex.scr.ms.employees.employees.infrastructure.adapter.in.controllers;

import com.invex.scr.ms.employees.employees.domain.exception.EmployeeNotFoundException;
import com.invex.scr.ms.employees.employees.domain.exception.InvalidEmployeeException;
import com.invex.scr.ms.employees.employees.domain.exception.InvalidQueryException;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(EmployeeNotFoundException.class)
  ProblemDetail handleNotFound(EmployeeNotFoundException ex) {
    return problem(HttpStatus.NOT_FOUND, WebConstants.NOT_FOUND_TITLE, ex.getMessage());
  }

  @ExceptionHandler({InvalidEmployeeException.class, InvalidQueryException.class})
  ProblemDetail handleInvalid(RuntimeException ex) {
    return problem(HttpStatus.BAD_REQUEST, WebConstants.INVALID_TITLE, ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  ProblemDetail handleUnexpected(Exception ex) {
    log.error("Error no controlado", ex);
    return problem(
        HttpStatus.INTERNAL_SERVER_ERROR,
        WebConstants.INTERNAL_TITLE,
        WebConstants.INTERNAL_ERROR_DETAIL);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    return ResponseEntity.badRequest()
        .body(
            problem(
                HttpStatus.BAD_REQUEST,
                WebConstants.VALIDATION_TITLE,
                WebConstants.UNREADABLE_DETAIL));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    List<Map<String, String>> errors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(error -> fieldError(error.getField(), error))
            .toList();
    return ResponseEntity.badRequest().body(validationProblem(errors));
  }

  @Override
  protected ResponseEntity<Object> handleHandlerMethodValidationException(
      HandlerMethodValidationException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    List<Map<String, String>> errors =
        ex.getParameterValidationResults().stream()
            .flatMap(result ->
                result.getResolvableErrors().stream()
                    .map(error ->
                        fieldError(
                            parameterName(
                                result.getMethodParameter().getParameterName(),
                                result.getContainerIndex(),
                                error),
                            error)))
            .toList();
    return ResponseEntity.badRequest().body(validationProblem(errors));
  }

  private static String parameterName(
      String parameter, Integer index, MessageSourceResolvable error) {
    String base = index == null ? parameter : parameter + "[" + index + "]";
    return error instanceof FieldError field ? base + "." + field.getField() : base;
  }

  private static Map<String, String> fieldError(String field, MessageSourceResolvable error) {
    return Map.of(
        "field", String.valueOf(field), "message", String.valueOf(error.getDefaultMessage()));
  }

  private static ProblemDetail validationProblem(List<Map<String, String>> errors) {
    ProblemDetail problem =
        problem(
            HttpStatus.BAD_REQUEST, WebConstants.VALIDATION_TITLE, WebConstants.VALIDATION_DETAIL);
    problem.setProperty(WebConstants.ERRORS_PROPERTY, errors);
    return problem;
  }

  private static ProblemDetail problem(HttpStatus status, String title, String detail) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
    problem.setTitle(title);
    return problem;
  }
}
