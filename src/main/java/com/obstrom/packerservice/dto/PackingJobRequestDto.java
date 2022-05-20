package com.obstrom.packerservice.dto;

import com.obstrom.packerservice.units.StandardUnitsUtil;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class PackingJobRequestDto {
        private final StandardUnitsUtil.Length lengthUnitType;
        private final StandardUnitsUtil.Weight weightUnitType;
        @Valid
        private final List<ContainerRequestDto> boxes;
        @Valid
        private final List<ItemRequestDto> products;
}
