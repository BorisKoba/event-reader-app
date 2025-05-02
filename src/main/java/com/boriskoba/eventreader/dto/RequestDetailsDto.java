package com.boriskoba.eventreader.dto;
import static com.boriskoba.eventreader.messages.ValidationErrorMessages.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record RequestDetailsDto(
    @NotBlank(message = MISSING_ID) String id,
    @NotNull(message = MISSING_DATE) LocalDateTime acceptDate,
    @NotBlank(message = MISSING_COMPANY) String sourceCompany
) {}
