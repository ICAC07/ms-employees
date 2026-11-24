package com.invex.scr.ms.employees.employees.infrastructure.adapter.in.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record EmployeePageResponse(
    List<EmployeeResponse> items,
    @Schema(description = "Cursor opaco para pedir la página siguiente") String nextCursor) {}
