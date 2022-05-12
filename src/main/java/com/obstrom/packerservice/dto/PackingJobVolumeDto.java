package com.obstrom.packerservice.dto;

public record PackingJobVolumeDto(
        Long totalJobVolume,
        Long totalJobVolumeLeft,
        Long totalJobVolumeUsed
) {
}
