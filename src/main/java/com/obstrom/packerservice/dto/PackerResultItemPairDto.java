package com.obstrom.packerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PackerResultItemPairDto {
    private final PackerResultItemDto item;
    private int quantity;

    @Data
    public static class PackerResultItemDto {
        private final String description;
        private final PackingJobResponseDto.DimensionsResponseDto dimensions;
    }
}
