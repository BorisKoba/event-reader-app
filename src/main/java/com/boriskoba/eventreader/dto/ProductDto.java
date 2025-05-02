package com.boriskoba.eventreader.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;
import static com.boriskoba.eventreader.messages.ValidationErrorMessages.*;
import java.time.LocalDate;

public record ProductDto(
    @NotBlank(message = MISSING_PRODUCT_TYPE) String type,
    @Positive(message = INCORRECT_PRICE_VALUE) int price,
    @NotNull(message = MISSING_DATE)  LocalDate startDate,
    @NotNull(message = MISSING_DATE) LocalDate endDate
) {}