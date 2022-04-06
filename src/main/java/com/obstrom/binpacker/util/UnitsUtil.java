package com.obstrom.binpacker.util;

import tech.units.indriya.unit.Units;

import javax.measure.MetricPrefix;
import javax.measure.Unit;

public class UnitsUtil {

    public static class Length {
        public static final Unit<javax.measure.quantity.Length> MILLIMETER = MetricPrefix.MILLI(Units.METRE);
        public static final Unit<javax.measure.quantity.Length> CENTIMETER = MetricPrefix.CENTI(Units.METRE);
        public static final Unit<javax.measure.quantity.Length> DECIMETER = MetricPrefix.DECI(Units.METRE);
        public static final Unit<javax.measure.quantity.Length> METER = Units.METRE;
    }

    public static class Weight {
        public static final Unit<javax.measure.quantity.Mass> GRAM = Units.GRAM;
        public static final Unit<javax.measure.quantity.Mass> KILOGRAM = Units.KILOGRAM;
    }

    public static class Volume {
        public static final Unit<javax.measure.quantity.Volume> CUBIC_MILLIMETER = MetricPrefix.MILLI(Units.CUBIC_METRE);
        public static final Unit<javax.measure.quantity.Volume> CUBIC_CENTIMETER = MetricPrefix.CENTI(Units.CUBIC_METRE);
        public static final Unit<javax.measure.quantity.Volume> CUBIC_DECIMETER = MetricPrefix.DECI(Units.CUBIC_METRE);
        public static final Unit<javax.measure.quantity.Volume> CUBIC_METER = Units.CUBIC_METRE;

        public static Unit<javax.measure.quantity.Volume> getVolumeByLengthUnit(Unit<javax.measure.quantity.Length> lengthUnit) {
            switch (lengthUnit.toString()) {
                case "mm" -> {
                    return CUBIC_MILLIMETER;
                }
                case "cm" -> {
                    return CUBIC_CENTIMETER;
                }
                case "dm" -> {
                    return CUBIC_DECIMETER;
                }
                default -> {
                    return CUBIC_METER;
                }
            }
        }
    }

}
