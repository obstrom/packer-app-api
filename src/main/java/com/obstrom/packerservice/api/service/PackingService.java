package com.obstrom.packerservice.api.service;

import com.github.skjolber.packing.api.Box;
import com.github.skjolber.packing.api.Container;
import com.github.skjolber.packing.api.StackableItem;
import com.github.skjolber.packing.visualizer.api.packaging.PackagingResultVisualizer;
import com.github.skjolber.packing.visualizer.packaging.DefaultPackagingResultVisualizerFactory;
import com.obstrom.packerservice.api.config.PackerProperties;
import com.obstrom.packerservice.core.packer.Packager;
import com.obstrom.packerservice.core.packer.PackingResults;
import com.obstrom.packerservice.core.units.LengthUnit;
import com.obstrom.packerservice.core.units.PackerUnit;
import com.obstrom.packerservice.core.units.WeightUnit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PackingService {

    private final DefaultPackagingResultVisualizerFactory visualizer;
    private final PackerProperties packerProperties;

    protected PackingResults pack(String jobId, List<Container> containers, List<StackableItem> products) {
        Packager packager = new Packager(jobId, this.packerProperties.getTimeoutMilliseconds(), containers);
        packager.init();
        packager.addProducts(products);
        return packager.pack();
    }

    protected Box boxBuilder(
            String id,
            String description,
            int width,
            int depth,
            int height,
            int weight,
            LengthUnit lengthUnitType,
            WeightUnit weightUnitType,
            boolean allow3DRotate
    ) {
        if (allow3DRotate) {
            return Box.newBuilder()
                    .withId(id)
                    .withDescription(description)
                    .withSize(handleLength(lengthUnitType, width),
                            handleLength(lengthUnitType, depth),
                            handleLength(lengthUnitType, height))
                    .withRotate3D()
                    .withWeight(handleWeight(weightUnitType, weight))
                    .build();
        } else {
            return Box.newBuilder()
                    .withId(id)
                    .withDescription(description)
                    .withSize(handleLength(lengthUnitType, width),
                            handleLength(lengthUnitType, depth),
                            handleLength(lengthUnitType, height))
                    .withWeight(handleWeight(weightUnitType, weight))
                    .build();
        }
    }

    protected Container containerBuilder(
            String id,
            String description,
            int width,
            int depth,
            int height,
            int weight,
            int maxLoad,
            LengthUnit lengthUnitType,
            WeightUnit weightUnitType
    ) {
        return Container.newBuilder()
                .withId(id)
                .withDescription(description)
                .withSize(handleLength(lengthUnitType, width),
                        handleLength(lengthUnitType, depth),
                        handleLength(lengthUnitType, height))
                .withEmptyWeight(handleWeight(weightUnitType, weight))
                .withMaxLoadWeight(handleWeight(weightUnitType, maxLoad))
                .build();
    }

    protected Integer calculateJobTotalWeight(List<Container> result) {
        return result.stream()
                .map(Container::getWeight)
                .reduce(0, Integer::sum);
    }

    protected double calculateUsedVolumePercentage(Long volumeCapacity, Long availableFreeVolume) {
        return 1.0d - (availableFreeVolume.doubleValue() / volumeCapacity.doubleValue());
    }

    protected PackagingResultVisualizer generateVisualizerData(List<Container> containers) {
        return visualizer.visualize(containers);
    }

    private int handleLength(LengthUnit unit, int value) {
        return PackerUnit.convert(unit.getUnit(), this.packerProperties.getSystemLengthUnit().getUnit(), value);
    }

    private int handleWeight(WeightUnit unit, int value) {
        return PackerUnit.convert(unit.getUnit(), this.packerProperties.getSystemWeightUnit().getUnit(), value);
    }

}
