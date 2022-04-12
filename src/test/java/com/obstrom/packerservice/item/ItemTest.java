package com.obstrom.packerservice.item;

import com.obstrom.packerservice.util.UnitsUtil;
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
        assertEquals(item.getWidth().getUnit(), UnitsUtil.Length.MILLIMETER);
        assertEquals(item.getHeight().getValue(), 20);
        assertEquals(item.getHeight().getUnit(), UnitsUtil.Length.MILLIMETER);
        assertEquals(item.getDepth().getValue(), 30);
        assertEquals(item.getDepth().getUnit(), UnitsUtil.Length.MILLIMETER);
        assertEquals(item.getWeight().getValue(), 200);
        assertEquals(item.getWeight().getUnit(), UnitsUtil.Weight.GRAM);
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
        Quantity<Length> widthAsMillimeter = item.getWidthAsUnit(UnitsUtil.Length.MILLIMETER);
        Quantity<Length> widthAsCentimeter = item.getWidthAsUnit(UnitsUtil.Length.CENTIMETER);
        Quantity<Length> widthAsDecimeter = item.getWidthAsUnit(UnitsUtil.Length.DECIMETER);
        Quantity<Length> widthAsMeter = item.getWidthAsUnit(UnitsUtil.Length.METER);

        // should
        assertEquals(widthAsMillimeter.getValue(), 5000);
        assertEquals(widthAsMillimeter.getUnit(), UnitsUtil.Length.MILLIMETER);
        assertEquals(widthAsCentimeter.getValue(), 500);
        assertEquals(widthAsCentimeter.getUnit(), UnitsUtil.Length.CENTIMETER);
        assertEquals(widthAsDecimeter.getValue(), 50);
        assertEquals(widthAsDecimeter.getUnit(), UnitsUtil.Length.DECIMETER);
        assertEquals(widthAsMeter.getValue(), 5);
        assertEquals(widthAsMeter.getUnit(), UnitsUtil.Length.METER);
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
        Quantity<Length> heightAsMillimeter = item.getHeightAsUnit(UnitsUtil.Length.MILLIMETER);
        Quantity<Length> heightAsCentimeter = item.getHeightAsUnit(UnitsUtil.Length.CENTIMETER);
        Quantity<Length> heightAsDecimeter = item.getHeightAsUnit(UnitsUtil.Length.DECIMETER);
        Quantity<Length> heightAsMeter = item.getHeightAsUnit(UnitsUtil.Length.METER);

        // should
        assertEquals(heightAsMillimeter.getValue(), 2000);
        assertEquals(heightAsMillimeter.getUnit(), UnitsUtil.Length.MILLIMETER);
        assertEquals(heightAsCentimeter.getValue(), 200);
        assertEquals(heightAsCentimeter.getUnit(), UnitsUtil.Length.CENTIMETER);
        assertEquals(heightAsDecimeter.getValue(), 20);
        assertEquals(heightAsDecimeter.getUnit(), UnitsUtil.Length.DECIMETER);
        assertEquals(heightAsMeter.getValue(), 2);
        assertEquals(heightAsMeter.getUnit(), UnitsUtil.Length.METER);
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
        Quantity<Length> depthAsMillimeter = item.getDepthAsUnit(UnitsUtil.Length.MILLIMETER);
        Quantity<Length> depthAsCentimeter = item.getDepthAsUnit(UnitsUtil.Length.CENTIMETER);
        Quantity<Length> depthAsDecimeter = item.getDepthAsUnit(UnitsUtil.Length.DECIMETER);
        Quantity<Length> depthAsMeter = item.getDepthAsUnit(UnitsUtil.Length.METER);

        // should
        assertEquals(depthAsMillimeter.getValue(), 3000);
        assertEquals(depthAsMillimeter.getUnit(), UnitsUtil.Length.MILLIMETER);
        assertEquals(depthAsCentimeter.getValue(), 300);
        assertEquals(depthAsCentimeter.getUnit(), UnitsUtil.Length.CENTIMETER);
        assertEquals(depthAsDecimeter.getValue(), 30);
        assertEquals(depthAsDecimeter.getUnit(), UnitsUtil.Length.DECIMETER);
        assertEquals(depthAsMeter.getValue(), 3);
        assertEquals(depthAsMeter.getUnit(), UnitsUtil.Length.METER);
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
        Quantity<Mass> weightAsGram = item.getWeightAsUnit(UnitsUtil.Weight.GRAM);
        Quantity<Mass> weightAsKilogram = item.getWeightAsUnit(UnitsUtil.Weight.KILOGRAM);

        // should
        assertEquals(weightAsGram.getValue(), 2000);
        assertEquals(weightAsGram.getUnit(), UnitsUtil.Weight.GRAM);
        assertEquals(weightAsKilogram.getValue(), 2);
        assertEquals(weightAsKilogram.getUnit(), UnitsUtil.Weight.KILOGRAM);
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
        assertEquals(item.getVolume().getUnit(), UnitsUtil.Volume.CUBIC_MILLIMETER);
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
        assertEquals(item.getVolume().getUnit(), UnitsUtil.Volume.CUBIC_MILLIMETER);
    }

    @Test
    public void givenDifferentUnit_thenItem_shouldHaveVolumeOfSameUnit() {
        // given
        double width = 0.5;
        double height = 0.2;
        double depth = 0.3;
        double weight = 200;

        // then
        Item item = new Item("Test item", UnitsUtil.Length.DECIMETER, UnitsUtil.Weight.GRAM, width, height, depth, weight);

        // should
        assertEquals(item.getVolume().getValue(), 0.03);
        assertEquals(item.getVolume().getUnit(), UnitsUtil.Volume.CUBIC_DECIMETER);
    }

}