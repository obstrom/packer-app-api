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
import javax.measure.quantity.Volume;
import java.util.Objects;

@Getter
public class Item {

    // TODO
    //  - Volume
    //  - Description
    //  - Hashable and equals?

    private final static Unit<Length> LENGTH_UNIT = MetricPrefix.MILLI(Units.METRE);
    private final static Unit<Mass> WEIGHT_UNIT = Units.GRAM;

    private final Unit<Length> lengthUnit;
    private final Unit<Mass> weightUnit;
    private Unit<Volume> volumeUnit;

    private final Quantity<Length> width;
    private final Quantity<Length> height;
    private final Quantity<Length> depth;
    private final Quantity<Mass> weight;
    private Quantity<Volume> volume;

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
        validate();
        calculateVolume();
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

    public void validate() {
        validateDimensionsNotNegative();
        validateWeightNotNegative();
    }

    public Quantity<Length> getWidthAsUnit(Unit<Length> unit) {
        return this.width.to(unit);
    }

    public Quantity<Length> getHeightAsUnit(Unit<Length> unit) {
        return this.height.to(unit);
    }

    public Quantity<Length> getDepthAsUnit(Unit<Length> unit) {
        return this.depth.to(unit);
    }

    public Quantity<Mass> getWeightAsUnit(Unit<Mass> unit) {
        return this.weight.to(unit);
    }

    private void calculateVolume() {
        Objects.requireNonNull(this.lengthUnit);

        final double volumeValue = this.getWidth().getValue().doubleValue() * this.getDepth().getValue().doubleValue() * this.getHeight().getValue().doubleValue();
        final Unit<Volume> volumeUnit = ItemUnits.Volume.getVolumeByLengthUnit(this.lengthUnit);

        this.volume = Quantities.getQuantity(volumeValue, volumeUnit);
        this.volumeUnit = volumeUnit;
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
