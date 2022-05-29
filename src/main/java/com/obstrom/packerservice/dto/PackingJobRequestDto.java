package com.obstrom.packerservice.dto;

import javax.validation.Valid;
import java.util.List;

public record PackingJobRequestDto(
        @Valid List<ContainerRequestDto> boxes,
        @Valid List<ItemRequestDto> products,
        boolean visualizer) {
}
