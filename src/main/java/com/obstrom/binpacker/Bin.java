package com.obstrom.binpacker;

import javax.measure.Quantity;
import javax.measure.quantity.Length;

public class Bin extends VolumetricWeightedEntity {

    public Bin(double width, double height, double depth) {
        super(width, height, depth);
    }
}
