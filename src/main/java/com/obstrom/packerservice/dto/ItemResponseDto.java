package com.obstrom.packerservice.dto;

public record ItemResponseDto(
        String description,
        DimensionsResponseDto dimensions,
        Integer quantity
) {
}
