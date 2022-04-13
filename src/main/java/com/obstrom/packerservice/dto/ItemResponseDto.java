package com.obstrom.packerservice.dto;

public record ItemResponseDto(
        String id,
        String description,
        long totalVolume,
        double volumeUsedPercent,
        int totalWeight,
        StackResponseDto content
) {
}
