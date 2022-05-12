package com.obstrom.packerservice.dto;

import com.github.skjolber.packing.visualizer.api.packaging.PackagingResultVisualizer;

import java.util.List;

public record PackingJobResponseDto(
        PackingJobVolumeDto volume,
        List<ItemResponseDto> boxes,
        PackagingResultVisualizer visualizeData
) {
}
