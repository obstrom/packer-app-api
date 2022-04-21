package com.obstrom.packerservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.obstrom.packerservice.StandardUnitsUtil;
import com.obstrom.packerservice.deserializer.PackingJobRequestDtoDeserializer;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@JsonDeserialize(using = PackingJobRequestDtoDeserializer.class)
public record PackingJobRequestDto(
        @NotNull StandardUnitsUtil.Length lengthUnitType,
        @NotNull StandardUnitsUtil.Weight weightUnitType,
        @NotNull @Valid List<ContainerRequestDto> boxes,
        @NotNull @Valid List<ItemRequestDto> products) {
}
