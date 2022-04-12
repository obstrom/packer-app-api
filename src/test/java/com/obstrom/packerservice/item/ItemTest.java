package com.obstrom.packerservice.item;

import com.obstrom.packerservice.StandardUnitsUtil;
import org.junit.jupiter.api.Test;

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
        Item item = new Item("Test item", width, height, depth, weight);

        // should
        assertEquals(item.getWidth().getValue(), 50);
        assertEquals(item.getWidth().getUnit(), StandardUnitsUtil.Length.METRIC_MILLIMETER.getUnit());
        assertEquals(item.getHeight().getValue(), 20);
        assertEquals(item.getHeight().getUnit(), StandardUnitsUtil.Length.METRIC_MILLIMETER.getUnit());
        assertEquals(item.getDepth().getValue(), 30);
        assertEquals(item.getDepth().getUnit(), StandardUnitsUtil.Length.METRIC_MILLIMETER.getUnit());
        assertEquals(item.getWeight().getValue(), 200);
        assertEquals(item.getWeight().getUnit(), StandardUnitsUtil.Weight.METRIC_GRAM.getUnit());
    }

    @Test
    public void givenTwoValidItemWithSameData_thenItems_shouldBeEqual() {
        // given
        int width = 50;
        int height = 20;
        int depth = 30;
        int weight = 200;

        Item itemOne = new Item("Test item", width, height, depth, weight);
        Item itemTwo = new Item("Test item", width, height, depth, weight);

        assertEquals(itemOne, itemTwo);
    }

    @Test
    public void givenTwoValidItemWithSameData_thenItems_shouldBeHashedToSameHash() {
        // given
        int width = 50;
        int height = 20;
        int depth = 30;
        int weight = 200;

        Item itemOne = new Item("Test item", width, height, depth, weight);
        Item itemTwo = new Item("Test item", width, height, depth, weight);

        assertEquals(itemOne.hashCode(), itemTwo.hashCode());
    }

    @Test
    public void givenValidItem_thenToString_shouldGiveCorrectFormattedString() {
        // given
        int width = 50;
        int height = 20;
        int depth = 30;
        int weight = 200;

        Item item = new Item("Test item", width, height, depth, weight);

        // then
        String expect = item.toString();

        // should
        assertEquals(expect, "MetricItem{ width: 50 mm, height: 20 mm, depth: 30 mm, volume: 30000 m㎥, weight: 200 G }");
    }

    @Test
    public void givenNegativeDimensions_shouldThrowException() {
        // given
        int width = -50;
        int height = 20;
        int depth = -30;
        int weight = 200;

        // then should
        assertThrows(IllegalStateException.class, () -> new Item("Test item", width, height, depth, weight));
    }

    @Test
    public void givenNegativeWeight_shouldThrowException() {
        // given
        int width = 50;
        int height = 20;
        int depth = 30;
        int weight = -200;

        // then should
        assertThrows(IllegalStateException.class, () -> new Item("Test item", width, height, depth, weight));
    }

    @Test
    public void givenValidItem_thenGettingWidthInOtherUnit_shouldReturnCorrect() {
        // given
        int width = 5000;
        int height = 20;
        int depth = 30;
        int weight = 200;
        Item item = new Item("Test item", width, height, depth, weight);

        // then
        Quantity<Length> widthAsMillimeter = item.getWidthAsUnit(StandardUnitsUtil.Length.METRIC_MILLIMETER.getUnit());
        Quantity<Length> widthAsCentimeter = item.getWidthAsUnit(StandardUnitsUtil.Length.METRIC_CENTIMETER.getUnit());
        Quantity<Length> widthAsDecimeter = item.getWidthAsUnit(StandardUnitsUtil.Length.METRIC_DECIMETER.getUnit());
        Quantity<Length> widthAsMeter = item.getWidthAsUnit(StandardUnitsUtil.Length.METRIC_METER.getUnit());

        // should
        assertEquals(widthAsMillimeter.getValue(), 5000);
        assertEquals(widthAsMillimeter.getUnit(), StandardUnitsUtil.Length.METRIC_MILLIMETER.getUnit());
        assertEquals(widthAsCentimeter.getValue(), 500);
        assertEquals(widthAsCentimeter.getUnit(), StandardUnitsUtil.Length.METRIC_CENTIMETER.getUnit());
        assertEquals(widthAsDecimeter.getValue(), 50);
        assertEquals(widthAsDecimeter.getUnit(), StandardUnitsUtil.Length.METRIC_DECIMETER.getUnit());
        assertEquals(widthAsMeter.getValue(), 5);
        assertEquals(widthAsMeter.getUnit(), StandardUnitsUtil.Length.METRIC_METER.getUnit());
    }

    @Test
    public void givenValidItem_thenGettingHeightInOtherUnit_shouldReturnCorrect() {
        // given
        int width = 50;
        int height = 2000;
        int depth = 30;
        int weight = 200;
        Item item = new Item("Test item", width, height, depth, weight);

        // then
        Quantity<Length> heightAsMillimeter = item.getHeightAsUnit(StandardUnitsUtil.Length.METRIC_MILLIMETER.getUnit());
        Quantity<Length> heightAsCentimeter = item.getHeightAsUnit(StandardUnitsUtil.Length.METRIC_CENTIMETER.getUnit());
        Quantity<Length> heightAsDecimeter = item.getHeightAsUnit(StandardUnitsUtil.Length.METRIC_DECIMETER.getUnit());
        Quantity<Length> heightAsMeter = item.getHeightAsUnit(StandardUnitsUtil.Length.METRIC_METER.getUnit());

        // should
        assertEquals(heightAsMillimeter.getValue(), 2000);
        assertEquals(heightAsMillimeter.getUnit(), StandardUnitsUtil.Length.METRIC_MILLIMETER.getUnit());
        assertEquals(heightAsCentimeter.getValue(), 200);
        assertEquals(heightAsCentimeter.getUnit(), StandardUnitsUtil.Length.METRIC_CENTIMETER.getUnit());
        assertEquals(heightAsDecimeter.getValue(), 20);
        assertEquals(heightAsDecimeter.getUnit(), StandardUnitsUtil.Length.METRIC_DECIMETER.getUnit());
        assertEquals(heightAsMeter.getValue(), 2);
        assertEquals(heightAsMeter.getUnit(), StandardUnitsUtil.Length.METRIC_METER.getUnit());
    }

    @Test
    public void givenValidItem_thenGettingDepthInOtherUnit_shouldReturnCorrect() {
        // given
        int width = 50;
        int height = 20;
        int depth = 3000;
        int weight = 200;
        Item item = new Item("Test item", width, height, depth, weight);

        // then
        Quantity<Length> depthAsMillimeter = item.getDepthAsUnit(StandardUnitsUtil.Length.METRIC_MILLIMETER.getUnit());
        Quantity<Length> depthAsCentimeter = item.getDepthAsUnit(StandardUnitsUtil.Length.METRIC_CENTIMETER.getUnit());
        Quantity<Length> depthAsDecimeter = item.getDepthAsUnit(StandardUnitsUtil.Length.METRIC_DECIMETER.getUnit());
        Quantity<Length> depthAsMeter = item.getDepthAsUnit(StandardUnitsUtil.Length.METRIC_METER.getUnit());

        // should
        assertEquals(depthAsMillimeter.getValue(), 3000);
        assertEquals(depthAsMillimeter.getUnit(), StandardUnitsUtil.Length.METRIC_MILLIMETER.getUnit());
        assertEquals(depthAsCentimeter.getValue(), 300);
        assertEquals(depthAsCentimeter.getUnit(), StandardUnitsUtil.Length.METRIC_CENTIMETER.getUnit());
        assertEquals(depthAsDecimeter.getValue(), 30);
        assertEquals(depthAsDecimeter.getUnit(), StandardUnitsUtil.Length.METRIC_DECIMETER.getUnit());
        assertEquals(depthAsMeter.getValue(), 3);
        assertEquals(depthAsMeter.getUnit(), StandardUnitsUtil.Length.METRIC_METER.getUnit());
    }

    @Test
    public void givenValidItem_thenGettingWeightInOtherUnit_shouldReturnCorrect() {
        // given
        int width = 50;
        int height = 20;
        int depth = 30;
        int weight = 2000;
        Item item = new Item("Test item", width, height, depth, weight);

        // then
        Quantity<Mass> weightAsGram = item.getWeightAsUnit(StandardUnitsUtil.Weight.METRIC_GRAM.getUnit());
        Quantity<Mass> weightAsKilogram = item.getWeightAsUnit(StandardUnitsUtil.Weight.METRIC_KILOGRAM.getUnit());

        // should
        assertEquals(weightAsGram.getValue(), 2000);
        assertEquals(weightAsGram.getUnit(), StandardUnitsUtil.Weight.METRIC_GRAM.getUnit());
        assertEquals(weightAsKilogram.getValue(), 2);
        assertEquals(weightAsKilogram.getUnit(), StandardUnitsUtil.Weight.METRIC_KILOGRAM.getUnit());
    }

    @Test
    public void givenValidDimensions_thenItem_shouldHaveCorrectVolume() {
        // given
        int width = 50;
        int height = 20;
        int depth = 30;
        int weight = 200;

        // then
        Item item = new Item("Test item", width, height, depth, weight);

        // should
        assertEquals(item.getVolume().getValue(), 30000);
        assertEquals(item.getVolume().getUnit(), StandardUnitsUtil.Volume.METRIC_CUBIC_MILLIMETER.getUnit());
    }

    @Test
    public void givenZeroDimension_thenItem_shouldHaveZeroVolume() {
        // given
        int width = 50;
        int height = 20;
        int depth = 0;
        int weight = 200;

        // then
        Item item = new Item("Test item", width, height, depth, weight);

        // should
        assertEquals(item.getVolume().getValue(), 0);
        assertEquals(item.getVolume().getUnit(), StandardUnitsUtil.Volume.METRIC_CUBIC_MILLIMETER.getUnit());
    }

    @Test
    public void givenDifferentUnit_thenItem_shouldHaveVolumeOfSameUnit() {
        // given
        double width = 0.5;
        double height = 0.2;
        double depth = 0.3;
        double weight = 200;

        // then
        Item item = new Item("Test item", StandardUnitsUtil.Length.METRIC_DECIMETER.getUnit(), StandardUnitsUtil.Weight.METRIC_GRAM.getUnit(), width, height, depth, weight);

        // should
        assertEquals(item.getVolume().getValue(), 0.03);
        assertEquals(item.getVolume().getUnit(), StandardUnitsUtil.Volume.METRIC_CUBIC_DECIMETER.getUnit());
    }

}