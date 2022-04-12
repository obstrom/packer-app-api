package com.obstrom.packerservice.packer;

import com.obstrom.packerservice.StandardUnitsUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import tech.units.indriya.quantity.Quantities;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Getter
@EqualsAndHashCode
public class Item {

    private final String description;

    private final Unit<Length> lengthUnit;
    private final Unit<Mass> weightUnit;
    private Unit<Volume> volumeUnit;

    private final Quantity<Length> width;
    private final Quantity<Length> height;
    private final Quantity<Length> depth;
    private final Quantity<Mass> weight;
    private Quantity<Volume> volume;

    private final List<Quantity<Length>> dimensionsBySize = new ArrayList<>();

    public Item(
            @NonNull String description,
            @NonNull Unit<Length> lengthUnit,
            @NonNull Unit<Mass> weightUnit,
            double width,
            double height,
            double depth,
            double weight
    ) {
        this.description = description;
        this.lengthUnit = lengthUnit;
        this.weightUnit = weightUnit;
        this.width = Quantities.getQuantity(width, this.lengthUnit);
        this.height = Quantities.getQuantity(height, this.lengthUnit);
        this.depth = Quantities.getQuantity(depth, this.lengthUnit);
        this.weight = Quantities.getQuantity(weight, this.weightUnit);
        validate();
        calculateVolume();
        fillAndSortDimensionsBySize();
    }

    public Item(
            @NonNull String description,
            double width,
            double height,
            double depth,
            double weight
    ) {
        this(description,
                StandardUnitsUtil.Length.METRIC_MILLIMETER.getUnit(),
                StandardUnitsUtil.Weight.METRIC_GRAM.getUnit(),
                width,
                height,
                depth,
                weight);
    }

    @Override
    public String toString() {
        return String.format(
                "MetricItem{ width: %s, height: %s, depth: %s, volume: %s, weight: %S }",
                this.getWidth(),
                this.getHeight(),
                this.getDepth(),
                this.getVolume(),
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
        final Unit<Volume> volumeUnit = StandardUnitsUtil.Volume.getVolumeByLengthUnit(this.lengthUnit);

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

    private void fillAndSortDimensionsBySize() {
        dimensionsBySize.addAll(List.of(width, height, depth));
        dimensionsBySize.sort(Comparator.comparingDouble(length -> length.getValue().intValue()));
    }

}
