package com.obstrom.binpacker;

import lombok.Getter;
import lombok.NonNull;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.measure.MetricPrefix;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;

@Getter
public class Item {

    // TODO
    //  - Dimensions
    //  - Weight
    //  - Description
    //  - Hashable and equals?

    private final static Unit<Length> LENGTH_UNIT = MetricPrefix.MILLI(Units.METRE);
    private final static Unit<Mass> WEIGHT_UNIT = Units.GRAM;

    private final Unit<Length> lengthUnit;
    private final Unit<Mass> weightUnit;
    //private final Unit<Volume> volumeUnit;

    private final Quantity<Length> width;
    private final Quantity<Length> height;
    private final Quantity<Length> depth;
    private final Quantity<Mass> weight;
    //private final Quantity<Volume> volume;

    public Item(
            @NonNull Unit<Length> lengthUnit,
            @NonNull Unit<Mass> weightUnit,
            double width,
            double height,
            double depth,
            double weight
    ) {
        this.lengthUnit = lengthUnit;
        this.weightUnit = weightUnit;
        this.width = Quantities.getQuantity(width, this.lengthUnit);
        this.height = Quantities.getQuantity(height, this.lengthUnit);
        this.depth = Quantities.getQuantity(depth, this.lengthUnit);
        this.weight = Quantities.getQuantity(weight, this.weightUnit);
        valid();
    }

    public Item(
            double width,
            double height,
            double depth,
            double weight
    ) {
        this(ItemUnits.Length.MILLIMETER, ItemUnits.Weight.GRAM, width, height, depth, weight);
    }

    @Override
    public String toString() {
        return String.format(
                "MetricItem{ width: %s, height: %s, depth: %s, weight: %S }",
                this.getWidth(),
                this.getHeight(),
                this.getDepth(),
                this.getWeight()
        );
    }

    public boolean valid() {
        validateDimensionsNotNegative();
        validateWeightNotNegative();
        return true;
    }

    private void validateDimensionsNotNegative() {
        if (this.height.getValue().intValue() < 0 || this.depth.getValue().intValue() < 0 || this.width.getValue().intValue() < 0) {
            throw new IllegalStateException("Item dimensions may not be negative");
        }
    }

    private void validateWeightNotNegative() {
        if (this.weight.getValue().intValue() < 0) throw new IllegalStateException("Item weight may not be negative");
    }

}
