package com.obstrom.packerservice.dto;

public record StackDimensionsResponseDto(
        Integer width,
        Integer depth,
        Integer height
) {
}
