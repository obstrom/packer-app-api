package com.obstrom.packerservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class PackerResultItemPairDto {

    @Getter
    private final PackerResultItemDto item;

    @Getter
    @Setter
    private int quantity;

    public record PackerResultItemDto(
            String description,
            PackingJobResponseDto.DimensionsResponseDto dimensions
    ) {
    }

}
