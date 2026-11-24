package com.invex.scr.ms.employees.employees.infrastructure.adapter.in.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import com.invex.scr.ms.employees.employees.domain.model.EmployeeRules;
import com.invex.scr.ms.employees.employees.domain.model.Gender;
import com.invex.scr.ms.employees.employees.infrastructure.adapter.in.controllers.WebConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record UpdateEmployeeRequest(
    @Pattern(regexp = ".*\\S.*", message = "no puede estar en blanco")
        @Size(max = EmployeeRules.MAX_NAME_LENGTH)
        String firstName,
    @Size(max = EmployeeRules.MAX_NAME_LENGTH) String middleName,
    @Pattern(regexp = ".*\\S.*", message = "no puede estar en blanco")
        @Size(max = EmployeeRules.MAX_NAME_LENGTH)
        String paternalSurname,
    @Size(max = EmployeeRules.MAX_NAME_LENGTH) String maternalSurname,
    @Min(0) @Max(EmployeeRules.MAX_AGE) Integer age,
    Gender gender,
    @Past
        @JsonFormat(pattern = WebConstants.DATE_PATTERN, lenient = OptBoolean.FALSE)
        @Schema(type = "string", example = "17-05-1990")
        LocalDate birthDate,
    @Pattern(regexp = ".*\\S.*", message = "no puede estar en blanco")
        @Size(max = EmployeeRules.MAX_JOB_TITLE_LENGTH)
        String jobTitle,
    Boolean active) {}
