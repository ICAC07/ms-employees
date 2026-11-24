package com.invex.scr.ms.employees.employees.application.service;

import com.invex.scr.ms.employees.employees.domain.exception.InvalidQueryException;

public record PaginationPolicy(int defaultLimit, int maxLimit) {

  public int resolve(Integer requested) {
    if (requested == null) {
      return defaultLimit;
    }
    if (requested < 1 || requested > maxLimit) {
      throw new InvalidQueryException("El límite debe estar entre 1 y " + maxLimit);
    }
    return requested;
  }
}
