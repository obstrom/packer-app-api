package com.obstrom.binpacker;

import org.junit.jupiter.api.Test;
import tech.units.indriya.unit.Units;

import javax.measure.MetricPrefix;
import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemTest {

    @Test
    public void givenValidDimensionsAndWeight_thenItem_shouldBeCreated() {
        // given
        int width = 50;
        int height = 20;
        int depth = 30;
        int weight = 200;

        // then
        Item item = new Item(width, height, depth, weight);

        // should
        assertEquals(item.getWidth().getValue(), 50);
        assertEquals(item.getWidth().getUnit(), ItemUnits.Length.MILLIMETER);
        assertEquals(item.getHeight().getValue(), 20);
        assertEquals(item.getHeight().getUnit(), ItemUnits.Length.MILLIMETER);
        assertEquals(item.getDepth().getValue(), 30);
        assertEquals(item.getDepth().getUnit(), ItemUnits.Length.MILLIMETER);
        assertEquals(item.getWeight().getValue(), 200);
        assertEquals(item.getWeight().getUnit(), ItemUnits.Weight.GRAM);
    }

    @Test
    public void givenNegativeDimensions_shouldThrowException() {
        // given
        int width = -50;
        int height = 20;
        int depth = -30;
        int weight = 200;

        // then should
        assertThrows(IllegalStateException.class, () -> new Item(width, height, depth, weight));
    }

    @Test
    public void givenNegativeWeight_shouldThrowException() {
        // given
        int width = 50;
        int height = 20;
        int depth = 30;
        int weight = -200;

        // then should
        assertThrows(IllegalStateException.class, () -> new Item(width, height, depth, weight));
    }

    @Test
    public void givenValidItem_thenGettingWidthInOtherUnit_shouldReturnCorrect() {
        // given
        int width = 5000;
        int height = 20;
        int depth = 30;
        int weight = 200;
        Item item = new Item(width, height, depth, weight);

        // then
        Quantity<Length> widthAsMillimeter = item.getWidthAsUnit(ItemUnits.Length.MILLIMETER);
        Quantity<Length> widthAsCentimeter = item.getWidthAsUnit(ItemUnits.Length.CENTIMETER);
        Quantity<Length> widthAsDecimeter = item.getWidthAsUnit(ItemUnits.Length.DECIMETER);
        Quantity<Length> widthAsMeter = item.getWidthAsUnit(ItemUnits.Length.METER);

        // should
        assertEquals(widthAsMillimeter.getValue(), 5000);
        assertEquals(widthAsMillimeter.getUnit(), ItemUnits.Length.MILLIMETER);
        assertEquals(widthAsCentimeter.getValue(), 500);
        assertEquals(widthAsCentimeter.getUnit(), ItemUnits.Length.CENTIMETER);
        assertEquals(widthAsDecimeter.getValue(), 50);
        assertEquals(widthAsDecimeter.getUnit(), ItemUnits.Length.DECIMETER);
        assertEquals(widthAsMeter.getValue(), 5);
        assertEquals(widthAsMeter.getUnit(), ItemUnits.Length.METER);
    }

    @Test
    public void givenValidItem_thenGettingHeightInOtherUnit_shouldReturnCorrect() {
        // given
        int width = 50;
        int height = 2000;
        int depth = 30;
        int weight = 200;
        Item item = new Item(width, height, depth, weight);

        // then
        Quantity<Length> heightAsMillimeter = item.getHeightAsUnit(ItemUnits.Length.MILLIMETER);
        Quantity<Length> heightAsCentimeter = item.getHeightAsUnit(ItemUnits.Length.CENTIMETER);
        Quantity<Length> heightAsDecimeter = item.getHeightAsUnit(ItemUnits.Length.DECIMETER);
        Quantity<Length> heightAsMeter = item.getHeightAsUnit(ItemUnits.Length.METER);

        // should
        assertEquals(heightAsMillimeter.getValue(), 2000);
        assertEquals(heightAsMillimeter.getUnit(), ItemUnits.Length.MILLIMETER);
        assertEquals(heightAsCentimeter.getValue(), 200);
        assertEquals(heightAsCentimeter.getUnit(), ItemUnits.Length.CENTIMETER);
        assertEquals(heightAsDecimeter.getValue(), 20);
        assertEquals(heightAsDecimeter.getUnit(), ItemUnits.Length.DECIMETER);
        assertEquals(heightAsMeter.getValue(), 2);
        assertEquals(heightAsMeter.getUnit(), ItemUnits.Length.METER);
    }

    @Test
    public void givenValidItem_thenGettingDepthInOtherUnit_shouldReturnCorrect() {
        // given
        int width = 50;
        int height = 20;
        int depth = 3000;
        int weight = 200;
        Item item = new Item(width, height, depth, weight);

        // then
        Quantity<Length> depthAsMillimeter = item.getDepthAsUnit(ItemUnits.Length.MILLIMETER);
        Quantity<Length> depthAsCentimeter = item.getDepthAsUnit(ItemUnits.Length.CENTIMETER);
        Quantity<Length> depthAsDecimeter = item.getDepthAsUnit(ItemUnits.Length.DECIMETER);
        Quantity<Length> depthAsMeter = item.getDepthAsUnit(ItemUnits.Length.METER);

        // should
        assertEquals(depthAsMillimeter.getValue(), 3000);
        assertEquals(depthAsMillimeter.getUnit(), ItemUnits.Length.MILLIMETER);
        assertEquals(depthAsCentimeter.getValue(), 300);
        assertEquals(depthAsCentimeter.getUnit(), ItemUnits.Length.CENTIMETER);
        assertEquals(depthAsDecimeter.getValue(), 30);
        assertEquals(depthAsDecimeter.getUnit(), ItemUnits.Length.DECIMETER);
        assertEquals(depthAsMeter.getValue(), 3);
        assertEquals(depthAsMeter.getUnit(), ItemUnits.Length.METER);
    }

    @Test
    public void givenValidItem_thenGettingWeightInOtherUnit_shouldReturnCorrect() {
        // given
        int width = 50;
        int height = 20;
        int depth = 30;
        int weight = 2000;
        Item item = new Item(width, height, depth, weight);

        // then
        Quantity<Mass> weightAsGram = item.getWeightAsUnit(ItemUnits.Weight.GRAM);
        Quantity<Mass> weightAsKilogram = item.getWeightAsUnit(ItemUnits.Weight.KILOGRAM);

        // should
        assertEquals(weightAsGram.getValue(), 2000);
        assertEquals(weightAsGram.getUnit(), ItemUnits.Weight.GRAM);
        assertEquals(weightAsKilogram.getValue(), 2);
        assertEquals(weightAsKilogram.getUnit(), ItemUnits.Weight.KILOGRAM);
    }

    @Test
    public void givenValidDimensions_thenItem_shouldHaveCorrectVolume() {
        // given
        int width = 50;
        int height = 20;
        int depth = 30;
        int weight = 200;

        // then
        Item item = new Item(width, height, depth, weight);

        // should
        assertEquals(item.getVolume().getValue(), 30000);
        assertEquals(item.getVolume().getUnit(), ItemUnits.Volume.CUBIC_MILLIMETER);
    }

    @Test
    public void givenZeroDimension_thenItem_shouldHaveZeroVolume() {
        // given
        int width = 50;
        int height = 20;
        int depth = 0;
        int weight = 200;

        // then
        Item item = new Item(width, height, depth, weight);

        // should
        assertEquals(item.getVolume().getValue(), 0);
        assertEquals(item.getVolume().getUnit(), ItemUnits.Volume.CUBIC_MILLIMETER);
    }

    @Test
    public void givenDifferentUnit_thenItem_shouldHaveVolumeOfSameUnit() {
        // given
        double width = 0.5;
        double height = 0.2;
        double depth = 0.3;
        double weight = 200;

        // then
        Item item = new Item(ItemUnits.Length.DECIMETER, ItemUnits.Weight.GRAM, width, height, depth, weight);

        // should
        assertEquals(item.getVolume().getValue(), 0.03);
        assertEquals(item.getVolume().getUnit(), ItemUnits.Volume.CUBIC_DECIMETER);
    }

}