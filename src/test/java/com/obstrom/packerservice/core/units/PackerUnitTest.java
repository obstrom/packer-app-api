package com.obstrom.packerservice.core.units;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PackerUnitTest {

    @Test
    void givenLengthUnit_whenConverting() {
        // Millimeter to X
        testLengthConversion(LengthUnit.METRIC_MILLIMETER, LengthUnit.METRIC_MILLIMETER, 1000, 1000);
        testLengthConversion(LengthUnit.METRIC_MILLIMETER, LengthUnit.METRIC_CENTIMETER, 1000, 100);
        testLengthConversion(LengthUnit.METRIC_MILLIMETER, LengthUnit.METRIC_DECIMETER, 1000, 10);
        testLengthConversion(LengthUnit.METRIC_MILLIMETER, LengthUnit.METRIC_METER, 1000, 1);

        // Centimeter to X
        testLengthConversion(LengthUnit.METRIC_CENTIMETER, LengthUnit.METRIC_MILLIMETER, 100, 1000);
        testLengthConversion(LengthUnit.METRIC_CENTIMETER, LengthUnit.METRIC_CENTIMETER, 100, 100);
        testLengthConversion(LengthUnit.METRIC_CENTIMETER, LengthUnit.METRIC_DECIMETER, 100, 10);
        testLengthConversion(LengthUnit.METRIC_CENTIMETER, LengthUnit.METRIC_METER, 100, 1);

        // Decimeter to X
        testLengthConversion(LengthUnit.METRIC_DECIMETER, LengthUnit.METRIC_MILLIMETER, 10, 1000);
        testLengthConversion(LengthUnit.METRIC_DECIMETER, LengthUnit.METRIC_CENTIMETER, 10, 100);
        testLengthConversion(LengthUnit.METRIC_DECIMETER, LengthUnit.METRIC_DECIMETER, 10, 10);
        testLengthConversion(LengthUnit.METRIC_DECIMETER, LengthUnit.METRIC_METER, 10, 1);

        // Meter to X
        testLengthConversion(LengthUnit.METRIC_METER, LengthUnit.METRIC_MILLIMETER, 1, 1000);
        testLengthConversion(LengthUnit.METRIC_METER, LengthUnit.METRIC_CENTIMETER, 1, 100);
        testLengthConversion(LengthUnit.METRIC_METER, LengthUnit.METRIC_DECIMETER, 1, 10);
        testLengthConversion(LengthUnit.METRIC_METER, LengthUnit.METRIC_METER, 1, 1);
    }

    @Test
    void givenWeightUnit_whenConverting() {
        // Gram to X
        testWeightConversion(WeightUnit.METRIC_GRAM, WeightUnit.METRIC_GRAM, 1000, 1000);
        testWeightConversion(WeightUnit.METRIC_GRAM, WeightUnit.METRIC_KILOGRAM, 1000, 1);

        // Kilogram to X
        testWeightConversion(WeightUnit.METRIC_KILOGRAM, WeightUnit.METRIC_GRAM, 1, 1000);
        testWeightConversion(WeightUnit.METRIC_KILOGRAM, WeightUnit.METRIC_KILOGRAM, 1, 1);
    }

    @Test
    void givenVolumeUnit_whenConverting() {
        // Cubic Millimeter to X
        testVolumeConversion(VolumeUnit.METRIC_CUBIC_MILLIMETER, VolumeUnit.METRIC_CUBIC_MILLIMETER, 1000, 1000);
        testVolumeConversion(VolumeUnit.METRIC_CUBIC_MILLIMETER, VolumeUnit.METRIC_CUBIC_CENTIMETER, 1000, 100);
        testVolumeConversion(VolumeUnit.METRIC_CUBIC_MILLIMETER, VolumeUnit.METRIC_CUBIC_DECIMETER, 1000, 10);
        testVolumeConversion(VolumeUnit.METRIC_CUBIC_MILLIMETER, VolumeUnit.METRIC_CUBIC_METER, 1000, 1);

        // Cubic Centimeter to X
        testVolumeConversion(VolumeUnit.METRIC_CUBIC_CENTIMETER, VolumeUnit.METRIC_CUBIC_MILLIMETER, 100, 1000);
        testVolumeConversion(VolumeUnit.METRIC_CUBIC_CENTIMETER, VolumeUnit.METRIC_CUBIC_CENTIMETER, 100, 100);
        testVolumeConversion(VolumeUnit.METRIC_CUBIC_CENTIMETER, VolumeUnit.METRIC_CUBIC_DECIMETER, 100, 10);
        testVolumeConversion(VolumeUnit.METRIC_CUBIC_CENTIMETER, VolumeUnit.METRIC_CUBIC_METER, 100, 1);

        // Cubic Decimeter to X
        testVolumeConversion(VolumeUnit.METRIC_CUBIC_DECIMETER, VolumeUnit.METRIC_CUBIC_MILLIMETER, 10, 1000);
        testVolumeConversion(VolumeUnit.METRIC_CUBIC_DECIMETER, VolumeUnit.METRIC_CUBIC_CENTIMETER, 10, 100);
        testVolumeConversion(VolumeUnit.METRIC_CUBIC_DECIMETER, VolumeUnit.METRIC_CUBIC_DECIMETER, 10, 10);
        testVolumeConversion(VolumeUnit.METRIC_CUBIC_DECIMETER, VolumeUnit.METRIC_CUBIC_METER, 10, 1);

        // Cubic Meter to X
        testVolumeConversion(VolumeUnit.METRIC_CUBIC_METER, VolumeUnit.METRIC_CUBIC_MILLIMETER, 1, 1000);
        testVolumeConversion(VolumeUnit.METRIC_CUBIC_METER, VolumeUnit.METRIC_CUBIC_CENTIMETER, 1, 100);
        testVolumeConversion(VolumeUnit.METRIC_CUBIC_METER, VolumeUnit.METRIC_CUBIC_DECIMETER, 1, 10);
        testVolumeConversion(VolumeUnit.METRIC_CUBIC_METER, VolumeUnit.METRIC_CUBIC_METER, 1, 1);
    }

    @Test
    void givenLength_whenGettingCorrespondingVolume_shouldBeCorrect() {
        assertEquals(VolumeUnit.getVolumeByLength(LengthUnit.METRIC_MILLIMETER), VolumeUnit.METRIC_CUBIC_MILLIMETER);
        assertEquals(VolumeUnit.getVolumeByLength(LengthUnit.METRIC_CENTIMETER), VolumeUnit.METRIC_CUBIC_CENTIMETER);
        assertEquals(VolumeUnit.getVolumeByLength(LengthUnit.METRIC_DECIMETER), VolumeUnit.METRIC_CUBIC_DECIMETER);
        assertEquals(VolumeUnit.getVolumeByLength(LengthUnit.METRIC_METER), VolumeUnit.METRIC_CUBIC_METER);
    }

    @Test
    void givenLengthUnit_whenGettingCorrespondingVolumeUnit_shouldBeCorrect() {
        assertEquals(VolumeUnit.getVolumeUnitByLengthUnit(LengthUnit.METRIC_MILLIMETER.getUnit()), VolumeUnit.METRIC_CUBIC_MILLIMETER.getUnit());
        assertEquals(VolumeUnit.getVolumeUnitByLengthUnit(LengthUnit.METRIC_CENTIMETER.getUnit()), VolumeUnit.METRIC_CUBIC_CENTIMETER.getUnit());
        assertEquals(VolumeUnit.getVolumeUnitByLengthUnit(LengthUnit.METRIC_DECIMETER.getUnit()), VolumeUnit.METRIC_CUBIC_DECIMETER.getUnit());
        assertEquals(VolumeUnit.getVolumeUnitByLengthUnit(LengthUnit.METRIC_METER.getUnit()), VolumeUnit.METRIC_CUBIC_METER.getUnit());
    }

    private void testLengthConversion(LengthUnit source, LengthUnit target, int value, int expected) {
        assertEquals(PackerUnit.convert(source.getUnit(), target.getUnit(), value), expected);
    }

    private void testWeightConversion(WeightUnit source, WeightUnit target, int value, int expected) {
        assertEquals(PackerUnit.convert(source.getUnit(), target.getUnit(), value), expected);
    }

    private void testVolumeConversion(VolumeUnit source, VolumeUnit target, int value, int expected) {
        assertEquals(PackerUnit.convert(source.getUnit(), target.getUnit(), value), expected);
    }

}
