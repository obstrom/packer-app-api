package com.obstrom.binpacker;

import org.junit.jupiter.api.Test;
import tec.units.ri.quantity.Quantities;
import tec.units.ri.unit.Units;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    public void itemShouldHaveWidthHeightDepthInMeters() {
        Item item = new Item(0.5, 0.5, 0.5);

        assertEquals(Units.METRE, item.getWidth().getUnit());
        assertEquals(Units.METRE, item.getHeight().getUnit());
        assertEquals(Units.METRE, item.getDepth().getUnit());
    }

}