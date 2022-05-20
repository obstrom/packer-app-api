package com.obstrom.packerservice.dto;

import com.github.skjolber.packing.visualizer.api.packaging.PackagingResultVisualizer;

import java.util.List;

public record PackingJobResponseDto(
        PackingJobVolumeDto volume,
        Integer totalWeight,
        Long packingTimeMs,
        List<ContainerResponseDto> boxes,
        PackagingResultVisualizer visualizeData
) {

    public record PackingJobVolumeDto(
            Long totalJobVolume,
            Long totalJobVolumeRemaining,
            Long totalJobVolumeUsed
    ) {
    }

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

    public record ContainerDetailsResponseDto(
            int itemsPlaced,
            long volumeLeft,
            int weightLeftToMaxWeight
    ) {
    }

    public record ItemResponseDto(
            String description,
            DimensionsResponseDto dimensions,
            Integer quantity
    ) {
    }

    public record DimensionsResponseDto(
            Integer width,
            Integer depth,
            Integer height
    ) {
    }

}
