package com.obstrom.packerservice.dto;

public record StackResponseDto(
        int dz, // TODO - Figure out what this value is referencing exactly
        int itemsPlaced,
        long volumeLeft,
        int weightLeftToMaxWeight
) {
}
