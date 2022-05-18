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
}
