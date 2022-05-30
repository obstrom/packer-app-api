package com.obstrom.packerservice.units;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.measure.MetricPrefix;
import javax.measure.Unit;

@Slf4j
public class StandardUnitsUtil {

    @Getter
    @AllArgsConstructor
    public enum Length {
        METRIC_MILLIMETER(MetricPrefix.MILLI(Units.METRE)),
        METRIC_CENTIMETER(MetricPrefix.CENTI(Units.METRE)),
        METRIC_DECIMETER(MetricPrefix.DECI(Units.METRE)),
        METRIC_METER(Units.METRE);

        private final Unit<javax.measure.quantity.Length> unit;

        // TODO - [Refactor] This could probably be made generic
        public static int convert(Length sourceUnit, Length targetUnit, int value) {
            if (sourceUnit.equals(targetUnit)) {
                log.debug("length unit was already {}, not converting", targetUnit);
                return value;
            }

            int convertedValue = Quantities
                    .getQuantity(value, sourceUnit.getUnit())
                    .to(targetUnit.getUnit())
                    .getValue().intValue();

            log.debug("successfully converted length {} {} to {} {}", value, sourceUnit.getUnit(), convertedValue, targetUnit.getUnit());
            return convertedValue;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Weight {
        METRIC_GRAM(Units.GRAM),
        METRIC_KILOGRAM(Units.KILOGRAM);

        private final Unit<javax.measure.quantity.Mass> unit;

        // TODO - [Refactor] This could probably be made generic
        public static int convert(Weight sourceUnit, Weight targetUnit, int value) {
            if (sourceUnit.equals(targetUnit)) {
                log.debug("weight unit was already {}, not converting", targetUnit);
                return value;
            }

            int convertedValue = Quantities
                    .getQuantity(value, sourceUnit.getUnit())
                    .to(targetUnit.getUnit())
                    .getValue().intValue();

            log.debug("successfully converted weight {} {} to {} {}", value, sourceUnit.getUnit(), convertedValue, targetUnit.getUnit());
            return convertedValue;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum Volume {
        METRIC_CUBIC_MILLIMETER(MetricPrefix.MILLI(Units.CUBIC_METRE)),
        METRIC_CUBIC_CENTIMETER(MetricPrefix.CENTI(Units.CUBIC_METRE)),
        METRIC_CUBIC_DECIMETER(MetricPrefix.DECI(Units.CUBIC_METRE)),
        METRIC_CUBIC_METER(Units.CUBIC_METRE);

        private final Unit<javax.measure.quantity.Volume> unit;

        public static Volume getVolumeByLength(Length lengthUnit) {
            switch (lengthUnit) {
                case METRIC_MILLIMETER -> {
                    return METRIC_CUBIC_MILLIMETER;
                }
                case METRIC_CENTIMETER -> {
                    return METRIC_CUBIC_CENTIMETER;
                }
                case METRIC_DECIMETER -> {
                    return METRIC_CUBIC_DECIMETER;
                }
                default -> {
                    return METRIC_CUBIC_METER;
                }
            }
        }

        public static Unit<javax.measure.quantity.Volume> getVolumeUnitByLengthUnit(Unit<javax.measure.quantity.Length> lengthUnit) {
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

        // TODO - [Refactor] This could probably be made generic
        public static int convert(Volume sourceUnit, Volume targetUnit, int value) {
            if (sourceUnit.equals(targetUnit)) {
                log.debug("volume unit was already {}, not converting", targetUnit);
                return value;
            }

            int convertedValue = Quantities
                    .getQuantity(value, sourceUnit.getUnit())
                    .to(targetUnit.getUnit())
                    .getValue().intValue();

            log.debug("successfully converted volume {} {} to {} {}", value, sourceUnit.getUnit(), convertedValue, targetUnit.getUnit());
            return convertedValue;
        }
    }

}
