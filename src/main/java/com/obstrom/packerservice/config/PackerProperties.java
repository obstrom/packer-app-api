package com.obstrom.packerservice.config;

import com.obstrom.packerservice.units.LengthUnit;
import com.obstrom.packerservice.units.WeightUnit;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("application.packer")
public class PackerProperties {

    private Long timeoutMilliseconds = 5000L;
    private LengthUnit systemLengthUnit = LengthUnit.METRIC_MILLIMETER;
    private WeightUnit systemWeightUnit = WeightUnit.METRIC_GRAM;

}
