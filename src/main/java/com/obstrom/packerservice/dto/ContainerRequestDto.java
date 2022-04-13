package com.obstrom.packerservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class ContainerRequestDto extends BaseItemRequestDto {

    @Min(0)
    @Max(Integer.MAX_VALUE)
    private int maxLoad = Integer.MAX_VALUE;

}
