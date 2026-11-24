package com.invex.scr.ms.employees.employees.infrastructure.adapter.in.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import com.invex.scr.ms.employees.employees.domain.model.EmployeeRules;
import com.invex.scr.ms.employees.employees.domain.model.Gender;
import com.invex.scr.ms.employees.employees.infrastructure.adapter.in.controllers.WebConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record CreateEmployeeRequest(
    @NotBlank @Size(max = EmployeeRules.MAX_NAME_LENGTH) @Schema(example = "Ana") String firstName,
    @Size(max = EmployeeRules.MAX_NAME_LENGTH) @Schema(example = "María") String middleName,
    @NotBlank @Size(max = EmployeeRules.MAX_NAME_LENGTH) @Schema(example = "García")
        String paternalSurname,
    @Size(max = EmployeeRules.MAX_NAME_LENGTH) @Schema(example = "López") String maternalSurname,
    @Min(0) @Max(EmployeeRules.MAX_AGE) @Schema(example = "36") Integer age,
    @NotNull Gender gender,
    @NotNull
        @Past
        @JsonFormat(pattern = WebConstants.DATE_PATTERN, lenient = OptBoolean.FALSE)
        @Schema(type = "string", example = "17-05-1990")
        LocalDate birthDate,
    @NotBlank @Size(max = EmployeeRules.MAX_JOB_TITLE_LENGTH) @Schema(example = "Ingeniera")
        String jobTitle,
    @Schema(description = "Por defecto true") Boolean active) {}
