package com.obstrom.binpacker.item;

import lombok.NonNull;

import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;

@Deprecated
public class Bin extends Item {

    public Bin(@NonNull String description, @NonNull Unit<Length> lengthUnit, @NonNull Unit<Mass> weightUnit, double width, double height, double depth, double weight) {
        super(description, lengthUnit, weightUnit, width, height, depth, weight);
    }

    public Bin(@NonNull String description, double width, double height, double depth, double weight) {
        super(description, width, height, depth, weight);
    }

}
