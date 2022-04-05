package com.obstrom.binpacker;

import org.junit.jupiter.api.Test;
import tech.units.indriya.unit.Units;

import javax.measure.MetricPrefix;

import static org.junit.jupiter.api.Assertions.*;

class ItemUnitsTest {

    @Test
    public void givenValidUnitLength_thenVolumeUnit_shouldBeCorrect() {
        // given
        var lengthMillimeter = MetricPrefix.MILLI(Units.METRE);
        var lengthCentimeter = MetricPrefix.CENTI(Units.METRE);
        var lengthDecimeter = MetricPrefix.DECI(Units.METRE);
        var lengthMeter = Units.METRE;

        // then
        var cubicMillimeter = ItemUnits.Volume.getVolumeByLengthUnit(lengthMillimeter);
        var cubicCentimeter = ItemUnits.Volume.getVolumeByLengthUnit(lengthCentimeter);
        var cubicDecimeter = ItemUnits.Volume.getVolumeByLengthUnit(lengthDecimeter);
        var cubicMeter = ItemUnits.Volume.getVolumeByLengthUnit(lengthMeter);

        // should
        assertEquals(cubicMillimeter, MetricPrefix.MILLI(Units.CUBIC_METRE));
        assertEquals(cubicCentimeter, MetricPrefix.CENTI(Units.CUBIC_METRE));
        assertEquals(cubicDecimeter, MetricPrefix.DECI(Units.CUBIC_METRE));
        assertEquals(cubicMeter, Units.CUBIC_METRE);
    }

}