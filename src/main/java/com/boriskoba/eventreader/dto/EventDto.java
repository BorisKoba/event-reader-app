package com.boriskoba.eventreader.dto;
import static com.boriskoba.eventreader.messages.ValidationErrorMessages.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record EventDto(
    @NotBlank(message = MISSING_ID) String id,
    @NotBlank(message = MISSING_PRODUCT_TYPE) String type,
    @NotBlank(message = MISSING_ID) String insuredId,
    @NotNull(message = MISSING_LIST_PRODUCTS) List<@NotNull ProductDto> products
) {}