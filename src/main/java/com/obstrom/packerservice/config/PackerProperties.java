package com.obstrom.packerservice.config;

import com.obstrom.packerservice.units.StandardUnitsUtil;
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
    private StandardUnitsUtil.Length systemLengthUnit = StandardUnitsUtil.Length.METRIC_MILLIMETER;
    private StandardUnitsUtil.Weight systemWeightUnit = StandardUnitsUtil.Weight.METRIC_GRAM;

}
