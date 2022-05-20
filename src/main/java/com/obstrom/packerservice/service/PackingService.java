package com.obstrom.packerservice.service;

import com.github.skjolber.packing.api.*;
import com.github.skjolber.packing.visualizer.api.packaging.PackagingResultVisualizer;
import com.github.skjolber.packing.visualizer.packaging.DefaultPackagingResultVisualizerFactory;
import com.obstrom.packerservice.dto.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@AllArgsConstructor
public class PackingService {

    private final DefaultPackagingResultVisualizerFactory visualizer;

    protected Box boxBuilder(String id, String description, int width, int depth, int height, int weight, boolean allow3DRotate) {
        if (allow3DRotate) {
            return Box.newBuilder()
                    .withId(id)
                    .withDescription(description)
                    .withSize(width, depth, height)
                    .withRotate3D()
                    .withWeight(weight)
                    .build();
        } else {
            return Box.newBuilder()
                    .withId(id)
                    .withDescription(description)
                    .withSize(width, depth, height)
                    .withWeight(weight)
                    .build();
        }
    }

    protected Integer calculateJobTotalWeight(List<Container> result) {
        return result.stream()
                .map(Container::getWeight)
                .reduce(0, Integer::sum);
    }

    protected PackingJobResponseDto.PackingJobVolumeDto calculateJobVolumeDto(List<Container> result) {
        AtomicLong totalVolume = new AtomicLong();
        AtomicLong totalVolumeRemaining = new AtomicLong();

        result.forEach(container -> {
            totalVolume.addAndGet(container.getVolume());
            totalVolumeRemaining.addAndGet(container.getStack().getFreeVolumeLoad());
        });

        Long totalVolumeUsed = totalVolume.get() - totalVolumeRemaining.get();

        return new PackingJobResponseDto.PackingJobVolumeDto(
                totalVolume.get(),
                totalVolumeRemaining.get(),
                totalVolumeUsed
        );
    }

    protected double calculateUsedVolumePercentage(Long volumeCapacity, Long availableFreeVolume) {
        return 1.0d - (availableFreeVolume.doubleValue() / volumeCapacity.doubleValue());
    }

    protected PackagingResultVisualizer generateVisualizerData(List<Container> containers) {
        return visualizer.visualize(containers);
    }

}
