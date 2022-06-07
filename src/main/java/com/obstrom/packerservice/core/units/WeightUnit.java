package com.obstrom.packerservice.core.units;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tech.units.indriya.unit.Units;

import javax.measure.Unit;

@Getter
@AllArgsConstructor
public enum WeightUnit implements PackerUnit<javax.measure.quantity.Mass> {
    METRIC_GRAM(Units.GRAM),
    METRIC_KILOGRAM(Units.KILOGRAM);

    private final Unit<javax.measure.quantity.Mass> unit;
}
