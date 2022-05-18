package com.obstrom.packerservice.dto;

import java.util.List;

public record ContainerResponseDto(
        String id,
        String description,
        long totalVolume,
        double volumeUsedPercentage,
        int totalWeight,
        ContainerDetailsResponseDto content,
        DimensionsResponseDto dimensions,
        List<ItemResponseDto> items
) {
}
