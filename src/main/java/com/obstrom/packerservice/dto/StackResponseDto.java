package com.obstrom.packerservice.dto;

public record StackResponseDto(
        long freeVolumeLoad,
        int freeWeightLoad,
        int height,
        int size
) {
}
