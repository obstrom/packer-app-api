package com.obstrom.binpacker;

import lombok.AllArgsConstructor;

import javax.measure.Quantity;
import javax.measure.quantity.Length;

public class Item extends VolumetricWeightedEntity {

    public Item(double width, double height, double depth) {
        super(width, height, depth);
    }

}
