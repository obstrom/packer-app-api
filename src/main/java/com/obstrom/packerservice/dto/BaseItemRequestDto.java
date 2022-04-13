package com.obstrom.packerservice.dto;

import com.obstrom.packerservice.StandardUnitsUtil;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BaseItemRequestDto {

    @NotBlank
    private String id;

    @NotBlank
    private String description;

    @Min(0)
    @Max(999_999)
    private int width;

    @Min(0)
    @Max(999_999)
    private int depth;

    @Min(0)
    @Max(999_999)
    private int height;

    @Min(0)
    @Max(999_999_999)
    private int weight;

}
