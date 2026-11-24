package com.invex.scr.ms.employees.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app")
public record AppProperties(
    @NotNull @Valid Security security, @NotNull @Valid Pagination pagination) {

  public record Security(@NotBlank String username, @NotBlank String password) {}

  public record Pagination(@Min(1) int defaultLimit, @Min(1) int maxLimit) {}
}
