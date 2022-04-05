package com.obstrom.binpacker;

import org.junit.jupiter.api.Test;
import tech.units.indriya.unit.Units;

import javax.measure.MetricPrefix;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MetricItemTest {

    @Test
    public void givenValidDimensionsAndWeight_thenItem_shouldBeCreated() {
        // given
        int width = 50;
        int height = 20;
        int depth = 30;
        int weight = 200;

        // then
        MetricItem metricItem = new MetricItem(width, height, depth, weight);

        // should
        assertEquals(metricItem.getWidth().getValue(), 50);
        assertEquals(metricItem.getWidth().getUnit(), MetricPrefix.MILLI(Units.METRE));
        assertEquals(metricItem.getHeight().getValue(), 20);
        assertEquals(metricItem.getHeight().getUnit(), MetricPrefix.MILLI(Units.METRE));
        assertEquals(metricItem.getDepth().getValue(), 30);
        assertEquals(metricItem.getDepth().getUnit(), MetricPrefix.MILLI(Units.METRE));
        assertEquals(metricItem.getWeight().getValue(), 200);
        assertEquals(metricItem.getWeight().getUnit(), Units.GRAM);
    }

}