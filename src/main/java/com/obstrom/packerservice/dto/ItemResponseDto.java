package com.obstrom.packerservice.dto;

public record ItemResponseDto(
        String id,
        String description,
        long totalVolume,
        double volumeUsedPercentage,
        int totalWeight,
        StackResponseDto content,
        StackDimensionsResponseDto dimensions
) {
}
