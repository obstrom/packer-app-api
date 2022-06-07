package com.obstrom.packerservice.core.units;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tech.units.indriya.unit.Units;

import javax.measure.MetricPrefix;
import javax.measure.Unit;

@Getter
@AllArgsConstructor
public enum LengthUnit implements PackerUnit<javax.measure.quantity.Length> {
    METRIC_MILLIMETER(MetricPrefix.MILLI(Units.METRE)),
    METRIC_CENTIMETER(MetricPrefix.CENTI(Units.METRE)),
    METRIC_DECIMETER(MetricPrefix.DECI(Units.METRE)),
    METRIC_METER(Units.METRE);

    private final Unit<javax.measure.quantity.Length> unit;
}
