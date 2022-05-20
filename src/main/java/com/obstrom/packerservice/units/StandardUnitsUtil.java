package com.obstrom.packerservice.units;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tech.units.indriya.unit.Units;

import javax.measure.MetricPrefix;
import javax.measure.Unit;

public class StandardUnitsUtil {

    @Getter
    @AllArgsConstructor
    public enum Length {
        METRIC_MILLIMETER(MetricPrefix.MILLI(Units.METRE)),
        METRIC_CENTIMETER(MetricPrefix.CENTI(Units.METRE)),
        METRIC_DECIMETER(MetricPrefix.DECI(Units.METRE)),
        METRIC_METER(Units.METRE);

        private final Unit<javax.measure.quantity.Length> unit;
    }

    @Getter
    @AllArgsConstructor
    public enum Weight {
        METRIC_GRAM(Units.GRAM),
        METRIC_KILOGRAM(Units.KILOGRAM);

        private final Unit<javax.measure.quantity.Mass> unit;
    }

    @Getter
    @AllArgsConstructor
    public enum Volume {
        METRIC_CUBIC_MILLIMETER(MetricPrefix.MILLI(Units.CUBIC_METRE)),
        METRIC_CUBIC_CENTIMETER(MetricPrefix.CENTI(Units.CUBIC_METRE)),
        METRIC_CUBIC_DECIMETER(MetricPrefix.DECI(Units.CUBIC_METRE)),
        METRIC_CUBIC_METER(Units.CUBIC_METRE);

        private final Unit<javax.measure.quantity.Volume> unit;

        public static Unit<javax.measure.quantity.Volume> getVolumeByLengthUnit(Unit<javax.measure.quantity.Length> lengthUnit) {
            switch (lengthUnit.toString()) {
                case "mm" -> {
                    return METRIC_CUBIC_MILLIMETER.getUnit();
                }
                case "cm" -> {
                    return METRIC_CUBIC_CENTIMETER.getUnit();
                }
                case "dm" -> {
                    return METRIC_CUBIC_DECIMETER.getUnit();
                }
                default -> {
                    return METRIC_CUBIC_METER.getUnit();
                }
            }
        }
    }

}
