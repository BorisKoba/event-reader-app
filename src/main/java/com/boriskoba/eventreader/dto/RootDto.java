package com.boriskoba.eventreader.dto;
import static com.boriskoba.eventreader.messages.ValidationErrorMessages.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RootDto(
    @NotNull(message = MISSING_REQUEST_DETAILS) RequestDetailsDto requestDetails,
    @NotNull(message = MISSING_LIST_EVENTS) List<@NotNull EventDto> events
) {}