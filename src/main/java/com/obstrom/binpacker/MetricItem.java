package com.obstrom.binpacker;

import tech.units.indriya.unit.Units;

import javax.measure.MetricPrefix;
import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;

public class MetricItem extends VolumetricWeightedEntity {

    // TODO
    //  - Dimensions
    //  - Weight
    //  - Description
    //  - Hashable and equals?

    private final static Unit<Length> LENGTH_UNIT = MetricPrefix.MILLI(Units.METRE);
    private final static Unit<Mass> WEIGHT_UNIT = Units.GRAM;

    public MetricItem(int width, int height, int depth, int weight) {
        super(
                LENGTH_UNIT,
                WEIGHT_UNIT,
                width,
                height,
                depth,
                weight
        );
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

}
