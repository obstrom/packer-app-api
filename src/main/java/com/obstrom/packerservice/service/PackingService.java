package com.obstrom.packerservice.service;

import com.github.skjolber.packing.api.*;
import com.github.skjolber.packing.visualizer.api.packaging.PackagingResultVisualizer;
import com.github.skjolber.packing.visualizer.packaging.DefaultPackagingResultVisualizerFactory;
import com.obstrom.packerservice.config.PackerProperties;
import com.obstrom.packerservice.dto.*;
import com.obstrom.packerservice.packer.Packager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PackingService {

    private final PackerProperties packerProperties;
    private final DefaultPackagingResultVisualizerFactory visualizer;

    private PackingJobResponseDto mapResultIntoResponseDto(Packager.PackingResults result) {
        List<Container> resultsContainers = result.resultsContainers();

        List<ContainerResponseDto> resultContainers = resultsContainers.stream()
                .map(container -> new ContainerResponseDto(
                        container.getId(),
                        container.getDescription(),
                        container.getVolume(),
                        calculateUsedVolumePercentage(container.getVolume(), container.getStack().getFreeVolumeLoad()),
                        container.getWeight(),
                        stackToResponseDto(container.getStack()),
                        stackValuesToResponseDto(container.getStackValues()),
                        stackPlacementsToResponseDto(container.getStack().getPlacements())
                ))
                .toList();

        PackingJobVolumeDto packingJobVolumeDto = calculateJobVolumeDto(resultsContainers);
        Integer totalWeight = calculateJobTotalWeight(resultsContainers);

        PackagingResultVisualizer visualizeData = generateVisualizerData(resultsContainers);

        return new PackingJobResponseDto(
                packingJobVolumeDto,
                totalWeight,
                result.runtimeMilliseconds(),
                resultContainers,
                visualizeData
        );
    }

    private ContainerDetailsResponseDto stackToResponseDto(Stack stack) {
        return new ContainerDetailsResponseDto(
                stack.getDz(),
                stack.getSize(),
                stack.getFreeVolumeLoad(),
                stack.getFreeWeightLoad()
        );
    }

    // TODO - Move DTO mapping to it's own Service class

    public PackingJobResponseDto handlePackingJobRequest(PackingJobRequestDto packingJobRequestDto) {
        List<StackableItem> products = mapItemRequestDtoToStackableItems(packingJobRequestDto.products());
        List<Container> containers = mapItemRequestDtoToContainers(packingJobRequestDto.boxes());

        Packager packager = new Packager(this.packerProperties.getTimeoutMilliseconds(), containers);
        packager.init();
        packager.addProducts(products);
        Packager.PackingResults result = packager.pack();

        return mapResultIntoResponseDto(result);
    }

    private List<StackableItem> mapItemRequestDtoToStackableItems(List<ItemRequestDto> items) {
        return items.stream()
                .map(dto -> new StackableItem(
                        boxBuilder(
                                dto.getId(),
                                dto.getDescription(),
                                dto.getWidth(),
                                dto.getDepth(),
                                dto.getHeight(),
                                dto.getWeight(),
                                dto.isAllowRotation()),
                        dto.getQuantity()))
                .collect(Collectors.toList());
    }

    private List<Container> mapItemRequestDtoToContainers(List<ContainerRequestDto> items) {
        return items.stream()
                .map(dto -> Container.newBuilder()
                        .withId(dto.getId())
                        .withDescription(dto.getDescription())
                        .withSize(dto.getWidth(), dto.getDepth(), dto.getHeight())
                        .withEmptyWeight(dto.getWeight())
                        .withMaxLoadWeight(dto.getMaxLoad())
                        .build())
                .collect(Collectors.toList());
    }

    private Box boxBuilder(String id, String description, int width, int depth, int height, int weight, boolean allow3DRotate) {
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

    private DimensionsResponseDto stackValuesToResponseDto(ContainerStackValue[] values) {
        ContainerStackValue stackValue = Arrays.stream(values).findFirst()
                .orElseThrow(() -> new IllegalStateException("No container stack values in results!"));

        return new DimensionsResponseDto(
                stackValue.getDx(),
                stackValue.getDy(),
                stackValue.getDz()
        );
    }

    private Integer calculateJobTotalWeight(List<Container> result) {
        return result.stream()
                .map(Container::getWeight)
                .reduce(0, Integer::sum);
    }

    private PackingJobVolumeDto calculateJobVolumeDto(List<Container> result) {
        AtomicLong totalVolume = new AtomicLong();
        AtomicLong totalVolumeRemaining = new AtomicLong();

        result.forEach(container -> {
            totalVolume.addAndGet(container.getVolume());
            totalVolumeRemaining.addAndGet(container.getStack().getFreeVolumeLoad());
        });

        Long totalVolumeUsed = totalVolume.get() - totalVolumeRemaining.get();

        return new PackingJobVolumeDto(
                totalVolume.get(),
                totalVolumeRemaining.get(),
                totalVolumeUsed
        );
    }

    private double calculateUsedVolumePercentage(Long volumeCapacity, Long availableFreeVolume) {
        return 1.0d - (availableFreeVolume.doubleValue() / volumeCapacity.doubleValue());
    }

    private List<ItemResponseDto> stackPlacementsToResponseDto(List<StackPlacement> placements) {
        HashMap<Integer, PackerResultItemWrapper> itemMap = new HashMap<>();

        placements.forEach(placement -> {
            StackValue stackValue = Arrays.stream(placement.getStackable().getStackValues()).findFirst()
                    .orElseThrow(() -> new IllegalStateException("No item stack values in results!"));

            PackerResultItem item = new PackerResultItem(
                    placement.getStackable().getDescription(),
                    new DimensionsResponseDto(stackValue.getDx(), stackValue.getDy(), stackValue.getDz())
            );

            int hash = item.hashCode();
            if (!itemMap.containsKey(hash)) {
                itemMap.put(hash, new PackerResultItemWrapper(item, 1));
            } else {
                PackerResultItemWrapper packerResultItemWrapper = itemMap.get(hash);
                packerResultItemWrapper.setQuantity(packerResultItemWrapper.getQuantity() + 1);
            }
        });

        return itemMap.values().stream()
                .map(itemWrapper -> new ItemResponseDto(
                        itemWrapper.item.description,
                        itemWrapper.item.dimensions,
                        itemWrapper.quantity
                ))
                .collect(Collectors.toList());
    }

    private record PackerResultItem(
            String description,
            DimensionsResponseDto dimensions
    ) {
    }

    @Getter
    @AllArgsConstructor
    private class PackerResultItemWrapper {
        private final PackerResultItem item;
        @Setter
        private int quantity;
    }

    private PackagingResultVisualizer generateVisualizerData(List<Container> containers) {
        return visualizer.visualize(containers);
    }

}
