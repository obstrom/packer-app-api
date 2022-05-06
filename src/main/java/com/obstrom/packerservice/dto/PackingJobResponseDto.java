package com.obstrom.packerservice.dto;

import com.github.skjolber.packing.visualizer.api.packaging.PackagingResultVisualizer;

import java.util.List;

public record PackingJobResponseDto(
        List<ItemResponseDto> boxes,
        PackagingResultVisualizer visualizeData
) {
}
