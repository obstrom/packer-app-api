package com.obstrom.packerservice.dto;

import java.util.List;

public record PackingJobResponseDto(
        List<ItemResponseDto> boxes
) {
}
