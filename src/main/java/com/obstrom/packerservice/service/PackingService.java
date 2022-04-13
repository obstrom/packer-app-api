package com.obstrom.packerservice.service;

import com.github.skjolber.packing.api.*;
import com.obstrom.packerservice.dto.*;
import com.obstrom.packerservice.packer.Packager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PackingService {

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
                        container.getWeight(),
                        container.getVolume(),
                        calculateUsedVolumePercentage(container.getVolume(), container.getStack().getFreeVolumeLoad()),
                        stackToResponseDto(container.getStack()))
                )
                .toList();

        return new PackingJobResponseDto(resultContainers);
    }

    private double calculateUsedVolumePercentage(Long volumeCapacity, Long availableFreeVolume) {
        return 1.0d - (availableFreeVolume.doubleValue() / volumeCapacity.doubleValue());
    }

    private StackResponseDto stackToResponseDto(Stack stack) {
        return new StackResponseDto(
                stack.getFreeVolumeLoad(),
                stack.getFreeWeightLoad(),
                stack.getDz(),
                stack.getSize()
        );
    }

}
