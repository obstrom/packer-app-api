package com.obstrom.binpacker;

import org.junit.jupiter.api.Test;
import tech.units.indriya.unit.Units;

import javax.measure.MetricPrefix;

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
        assertEquals(item.getWidth().getUnit(), MetricPrefix.MILLI(Units.METRE));
        assertEquals(item.getHeight().getValue(), 20);
        assertEquals(item.getHeight().getUnit(), MetricPrefix.MILLI(Units.METRE));
        assertEquals(item.getDepth().getValue(), 30);
        assertEquals(item.getDepth().getUnit(), MetricPrefix.MILLI(Units.METRE));
        assertEquals(item.getWeight().getValue(), 200);
        assertEquals(item.getWeight().getUnit(), Units.GRAM);
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

}