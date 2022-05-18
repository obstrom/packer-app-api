package com.obstrom.packerservice.dto;

public record ContainerDetailsResponseDto(
        int dz, // TODO - Figure out what this value is referencing exactly
        int itemsPlaced,
        long volumeLeft,
        int weightLeftToMaxWeight
) {
}
