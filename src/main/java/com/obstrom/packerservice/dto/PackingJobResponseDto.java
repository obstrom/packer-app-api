package com.obstrom.packerservice.dto;

import com.github.skjolber.packing.visualizer.api.packaging.PackagingResultVisualizer;
import com.obstrom.packerservice.units.StandardUnitsUtil;

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
            Long totalJobVolumeUsed,
            StandardUnitsUtil.Volume unit
    ) {
    }

    public record ContainerResponseDto(
            String id,
            String description,
            long totalVolume,
            double volumeUsedPercentage,
            int totalWeight,
            StandardUnitsUtil.Weight weightUnit,
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
            StandardUnitsUtil.Length unit,
            Integer width,
            Integer depth,
            Integer height
    ) {
    }

}
