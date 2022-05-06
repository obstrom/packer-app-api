package com.obstrom.packerservice.service;

import com.github.skjolber.packing.api.Box;
import com.github.skjolber.packing.api.Container;
import com.github.skjolber.packing.api.Stack;
import com.github.skjolber.packing.api.StackableItem;
import com.github.skjolber.packing.visualizer.api.packaging.PackagingResultVisualizer;
import com.github.skjolber.packing.visualizer.packaging.DefaultPackagingResultVisualizerFactory;
import com.obstrom.packerservice.dto.*;
import com.obstrom.packerservice.packer.Packager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PackingService {

    private final DefaultPackagingResultVisualizerFactory visualizer;

    public PackingService(DefaultPackagingResultVisualizerFactory visualizer) {
        this.visualizer = visualizer;
    }

    public PackingJobResponseDto handlePackingJobRequest(PackingJobRequestDto packingJobRequestDto) {
        List<StackableItem> products = mapItemRequestDtoToStackableItems(packingJobRequestDto.products());
        List<Container> containers = mapItemRequestDtoToContainers(packingJobRequestDto.boxes());

        Packager packager = new Packager(containers);
        packager.init();
        packager.addProducts(products);
        List<Container> result = packager.pack();

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

    private PackingJobResponseDto mapResultIntoResponseDto(List<Container> result) {
        List<ItemResponseDto> resultContainers = result.stream()
                .map(container -> new ItemResponseDto(
                        container.getId(),
                        container.getDescription(),
                        container.getVolume(),
                        calculateUsedVolumePercentage(container.getVolume(), container.getStack().getFreeVolumeLoad()),
                        container.getWeight(),
                        stackToResponseDto(container.getStack()))
                )
                .toList();

        PackagingResultVisualizer visualizeData = generateVisualizerData(result);

        return new PackingJobResponseDto(resultContainers, visualizeData);
    }

    private double calculateUsedVolumePercentage(Long volumeCapacity, Long availableFreeVolume) {
        log.info("volumeCapacity = {}", volumeCapacity);
        log.info("availableFreeVolume = {}", availableFreeVolume);
        return 1.0d - (availableFreeVolume.doubleValue() / volumeCapacity.doubleValue());
    }

    private StackResponseDto stackToResponseDto(Stack stack) {
        return new StackResponseDto(
                stack.getDz(),
                stack.getSize(),
                stack.getFreeVolumeLoad(),
                stack.getFreeWeightLoad()
        );
    }

    private PackagingResultVisualizer generateVisualizerData(List<Container> containers) {
        return visualizer.visualize(containers);
    }

}
