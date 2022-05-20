package com.obstrom.packerservice.dto;

import com.obstrom.packerservice.units.StandardUnitsUtil;

import javax.validation.Valid;
import java.util.List;

public record PackingJobRequestDto(
        StandardUnitsUtil.Length lengthUnitType,
        StandardUnitsUtil.Weight weightUnitType,
        @Valid List<ContainerRequestDto> boxes,
        @Valid List<ItemRequestDto> products) {
}
