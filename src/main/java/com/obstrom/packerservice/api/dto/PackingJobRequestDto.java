package com.obstrom.packerservice.api.dto;

import org.springframework.lang.Nullable;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public record PackingJobRequestDto(
        @Valid List<ContainerRequestDto> boxes,
        @Valid List<ItemRequestDto> products,
        boolean visualizer,
        @Nullable UUID jobId) {
}
