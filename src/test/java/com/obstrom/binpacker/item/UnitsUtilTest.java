package com.obstrom.binpacker.item;

import com.obstrom.binpacker.util.UnitsUtil;
import org.junit.jupiter.api.Test;
import tech.units.indriya.unit.Units;

import javax.measure.MetricPrefix;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnitsUtilTest {

    @Test
    public void givenValidUnitLength_thenVolumeUnit_shouldBeCorrect() {
        // given
        var lengthMillimeter = MetricPrefix.MILLI(Units.METRE);
        var lengthCentimeter = MetricPrefix.CENTI(Units.METRE);
        var lengthDecimeter = MetricPrefix.DECI(Units.METRE);
        var lengthMeter = Units.METRE;

        // then
        var cubicMillimeter = UnitsUtil.Volume.getVolumeByLengthUnit(lengthMillimeter);
        var cubicCentimeter = UnitsUtil.Volume.getVolumeByLengthUnit(lengthCentimeter);
        var cubicDecimeter = UnitsUtil.Volume.getVolumeByLengthUnit(lengthDecimeter);
        var cubicMeter = UnitsUtil.Volume.getVolumeByLengthUnit(lengthMeter);

        // should
        assertEquals(cubicMillimeter, MetricPrefix.MILLI(Units.CUBIC_METRE));
        assertEquals(cubicCentimeter, MetricPrefix.CENTI(Units.CUBIC_METRE));
        assertEquals(cubicDecimeter, MetricPrefix.DECI(Units.CUBIC_METRE));
        assertEquals(cubicMeter, Units.CUBIC_METRE);
    }

}