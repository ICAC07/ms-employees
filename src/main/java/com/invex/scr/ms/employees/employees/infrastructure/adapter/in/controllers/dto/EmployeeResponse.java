package com.invex.scr.ms.employees.employees.infrastructure.adapter.in.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.invex.scr.ms.employees.employees.domain.model.Gender;
import com.invex.scr.ms.employees.employees.infrastructure.adapter.in.controllers.WebConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.time.LocalDate;

public record EmployeeResponse(
    Long id,
    String firstName,
    String middleName,
    String paternalSurname,
    String maternalSurname,
    int age,
    Gender gender,
    @JsonFormat(pattern = WebConstants.DATE_PATTERN)
        @Schema(type = "string", example = "17-05-1990")
        LocalDate birthDate,
    String jobTitle,
    Instant createdAt,
    boolean active) {}
