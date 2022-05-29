package com.obstrom.packerservice;

import com.obstrom.packerservice.units.StandardUnitsUtil;
import org.junit.jupiter.api.Test;
import tech.units.indriya.unit.Units;

import javax.measure.MetricPrefix;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StandardUnitsUtilTest {

    @Test
    public void givenValidUnitLength_thenVolumeUnit_shouldBeCorrect() {
        // given
        var lengthMillimeter = MetricPrefix.MILLI(Units.METRE);
        var lengthCentimeter = MetricPrefix.CENTI(Units.METRE);
        var lengthDecimeter = MetricPrefix.DECI(Units.METRE);
        var lengthMeter = Units.METRE;

        // then
        var cubicMillimeter = StandardUnitsUtil.Volume.getVolumeUnitByLengthUnit(lengthMillimeter);
        var cubicCentimeter = StandardUnitsUtil.Volume.getVolumeUnitByLengthUnit(lengthCentimeter);
        var cubicDecimeter = StandardUnitsUtil.Volume.getVolumeUnitByLengthUnit(lengthDecimeter);
        var cubicMeter = StandardUnitsUtil.Volume.getVolumeUnitByLengthUnit(lengthMeter);

        // should
        assertEquals(cubicMillimeter, MetricPrefix.MILLI(Units.CUBIC_METRE));
        assertEquals(cubicCentimeter, MetricPrefix.CENTI(Units.CUBIC_METRE));
        assertEquals(cubicDecimeter, MetricPrefix.DECI(Units.CUBIC_METRE));
        assertEquals(cubicMeter, Units.CUBIC_METRE);
    }

}