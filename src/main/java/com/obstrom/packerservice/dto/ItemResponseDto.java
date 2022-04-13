package com.obstrom.packerservice.dto;

public record ItemResponseDto(
        String id,
        String description,
        int totalWeight,
        long totalVolume,
        double volumeUsedPercent,
        StackResponseDto stack
) {
}
