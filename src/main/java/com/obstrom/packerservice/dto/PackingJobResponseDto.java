package com.obstrom.packerservice.dto;

import com.github.skjolber.packing.visualizer.api.packaging.PackagingResultVisualizer;
import lombok.Data;

import java.util.List;

@Data
public class PackingJobResponseDto {

    private final PackingJobVolumeDto volume;
    private final Integer totalWeight;
    private final Long packingTimeMs;
    private final List<ContainerResponseDto> boxes;
    private final PackagingResultVisualizer visualizeData;

    @Data
    public static class PackingJobVolumeDto {
        private final Long totalJobVolume;
        private final Long totalJobVolumeRemaining;
        private final Long totalJobVolumeUsed;
    }

    @Data
    public static class ContainerResponseDto {
        private final String id;
        private final String description;
        private final long totalVolume;
        private final double volumeUsedPercentage;
        private final int totalWeight;
        private final ContainerDetailsResponseDto content;
        private final DimensionsResponseDto dimensions;
        private final List<ItemResponseDto> items;
    }

    @Data
    public static class ContainerDetailsResponseDto {
        private final int itemsPlaced;
        private final long volumeLeft;
        private final int weightLeftToMaxWeight;
    }

    @Data
    public static class ItemResponseDto {
        private final String description;
        private final DimensionsResponseDto dimensions;
        private final Integer quantity;
    }

    @Data
    public static class DimensionsResponseDto {
        private final Integer width;
        private final Integer depth;
        private final Integer height;
    }

}
