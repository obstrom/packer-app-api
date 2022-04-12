package com.obstrom.packerservice.dto;

import com.obstrom.packerservice.StandardUnitsUtil;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

// TODO - Move Min and Max values to a Config file
public record ItemRequestDto(
        @NotBlank String description,
        StandardUnitsUtil.Length lengthUnitType,
        StandardUnitsUtil.Weight weightUnitType,
        @Min(0) @Max(999_999) double width,
        @Min(0) @Max(999_999) double depth,
        @Min(0) @Max(999_999) double height,
        @Min(0) @Max(999_999_999) double weight) {
}
