package com.obstrom.packerservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class ItemRequestDto extends BaseItemRequestDto {

    boolean allowRotation;

    @Min(0)
    @Max(999)
    private int quantity;

}
