package com.obstrom.binpacker;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import tech.units.indriya.quantity.Quantities;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;

@Getter
@Setter
public abstract class VolumetricWeightedEntity {

    // https://www.baeldung.com/javax-measure

    // TODO:
    //  - Methods for getting values in other units (cm instead of mm)
    //  - Implement volume and add automatic calc of volume

    private final Unit<Length> lengthUnit;
    private final Unit<Mass> weightUnit;

    private Quantity<Length> width;
    private Quantity<Length> height;
    private Quantity<Length> depth;

    private Quantity<Mass> weight;
    private Quantity<Volume> volume;

    public VolumetricWeightedEntity(
            @NonNull Unit<Length> lengthUnit,
            @NonNull Unit<Mass> weightUnit,
            int width,
            int height,
            int depth,
            int weight
    ) {
        this.lengthUnit = lengthUnit;
        this.weightUnit = weightUnit;
        this.width = Quantities.getQuantity(width, this.lengthUnit);
        this.height = Quantities.getQuantity(height, this.lengthUnit);
        this.depth = Quantities.getQuantity(depth, this.lengthUnit);
        this.weight = Quantities.getQuantity(weight, this.weightUnit);
    }

}
