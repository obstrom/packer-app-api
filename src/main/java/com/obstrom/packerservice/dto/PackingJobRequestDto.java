package com.obstrom.packerservice.dto;

import javax.validation.Valid;
import java.util.List;

public record PackingJobRequestDto(
        @Valid List<ItemRequestDto> boxes,
        @Valid List<ItemRequestDto> products,
        Boolean allowRotation) {
}
