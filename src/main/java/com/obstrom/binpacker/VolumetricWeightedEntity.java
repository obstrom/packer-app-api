package com.obstrom.binpacker;

import lombok.Getter;
import lombok.Setter;
import tec.units.ri.quantity.Quantities;
import tec.units.ri.unit.Units;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Length;

@Getter
@Setter
public abstract class VolumetricWeightedEntity {

    // https://www.baeldung.com/javax-measure

    private Quantity<Length> width;
    private Quantity<Length> height;
    private Quantity<Length> depth;

    // private Quantity<Mass> weight;
    // private Quantity<Volume> volume;

    public VolumetricWeightedEntity(double width, double height, double depth) {
        this.width = Quantities.getQuantity(width, Units.METRE);
        this.height = Quantities.getQuantity(height, Units.METRE);
        this.depth = Quantities.getQuantity(depth, Units.METRE);
    }

}
