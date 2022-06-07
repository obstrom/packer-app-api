package com.obstrom.packerservice.service;

import com.github.skjolber.packing.api.*;
import com.github.skjolber.packing.visualizer.api.packaging.PackagingResultVisualizer;
import com.obstrom.packerservice.config.PackerProperties;
import com.obstrom.packerservice.dto.ContainerRequestDto;
import com.obstrom.packerservice.dto.ItemRequestDto;
import com.obstrom.packerservice.dto.PackerResultItemPairDto;
import com.obstrom.packerservice.dto.PackingJobResponseDto;
import com.obstrom.packerservice.packer.PackingResults;
import com.obstrom.packerservice.units.VolumeUnit;
import lombok.AllArgsConstructor;
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
public class DtoService {

    private final PackingService packingService;
    private final PackerProperties packerProperties;

    public PackingJobResponseDto mapPackingJobResponseDto(PackingResults result, boolean visualizer) {
        PackagingResultVisualizer visualizeData = (visualizer)
                ? packingService.generateVisualizerData(result.resultsContainers())
                : null;

        return new PackingJobResponseDto(
                mapPackingJobVolumeDto(result.resultsContainers()),
                packingService.calculateJobTotalWeight(result.resultsContainers()),
                result.runtimeMilliseconds(),
                mapContainerResponseDtoList(result.resultsContainers()),
                visualizeData
        );
    }

    public List<StackableItem> mapStackableItemList(List<ItemRequestDto> items) {
        return items.stream()
                .map(this::mapStackableItem)
                .collect(Collectors.toList());
    }

    public List<Container> mapContainerList(List<ContainerRequestDto> items) {
        return items.stream()
                .map(this::mapContainer)
                .collect(Collectors.toList());
    }

    public List<PackingJobResponseDto.ContainerResponseDto> mapContainerResponseDtoList(List<Container> resultsContainers) {
        return resultsContainers.stream()
                .map(this::mapContainerResponseDto)
                .toList();
    }

    public PackingJobResponseDto.ContainerResponseDto mapContainerResponseDto(Container container) {
        double volumeUsedPercentage = packingService.calculateUsedVolumePercentage(
                container.getVolume(),
                container.getStack().getFreeVolumeLoad()
        );

        return new PackingJobResponseDto.ContainerResponseDto(
                container.getId(),
                container.getDescription(),
                container.getVolume(),
                volumeUsedPercentage,
                container.getWeight(),
                this.packerProperties.getSystemWeightUnit(),
                mapContainerDetailsResponseDto(container.getStack()),
                mapDimensionsResponseDto(container.getStackValues()),
                mapItemResponseDtoList(container.getStack().getPlacements())
        );
    }

    public StackableItem mapStackableItem(ItemRequestDto dto) {
        return new StackableItem(
                packingService.boxBuilder(
                        dto.getId(),
                        dto.getDescription(),
                        dto.getWidth(),
                        dto.getDepth(),
                        dto.getHeight(),
                        dto.getWeight(),
                        dto.getLengthUnitType(),
                        dto.getWeightUnitType(),
                        dto.isAllowRotation()),
                dto.getQuantity());
    }

    public Container mapContainer(ContainerRequestDto dto) {
        return packingService.containerBuilder(
                dto.getId(),
                dto.getDescription(),
                dto.getWidth(),
                dto.getDepth(),
                dto.getHeight(),
                dto.getWeight(),
                dto.getMaxLoad(),
                dto.getLengthUnitType(),
                dto.getWeightUnitType()
        );
    }

    public PackingJobResponseDto.ContainerDetailsResponseDto mapContainerDetailsResponseDto(Stack stack) {
        return new PackingJobResponseDto.ContainerDetailsResponseDto(
                stack.getSize(),
                stack.getFreeVolumeLoad(),
                stack.getFreeWeightLoad()
        );
    }

    public PackingJobResponseDto.DimensionsResponseDto mapDimensionsResponseDto(ContainerStackValue[] values) {
        ContainerStackValue stackValue = Arrays.stream(values).findFirst()
                .orElseThrow(() -> new IllegalStateException("No container stack values in results!"));

        return new PackingJobResponseDto.DimensionsResponseDto(
                this.packerProperties.getSystemLengthUnit(),
                stackValue.getDx(),
                stackValue.getDy(),
                stackValue.getDz()
        );
    }

    public List<PackingJobResponseDto.ItemResponseDto> mapItemResponseDtoList(List<StackPlacement> placements) {
        return createPackerResultItemPairDtoHashMap(placements)
                .values().stream()
                .map(itemPair -> new PackingJobResponseDto.ItemResponseDto(
                        itemPair.getItem().description(),
                        itemPair.getItem().dimensions(),
                        itemPair.getQuantity()
                ))
                .collect(Collectors.toList());
    }

    public HashMap<Integer, PackerResultItemPairDto> createPackerResultItemPairDtoHashMap(List<StackPlacement> placements) {
        HashMap<Integer, PackerResultItemPairDto> itemMap = new HashMap<>();

        placements.forEach(placement -> {
            StackValue stackValue = Arrays.stream(placement.getStackable().getStackValues()).findFirst()
                    .orElseThrow(() -> new IllegalStateException("No item stack values in results!"));

            PackerResultItemPairDto.PackerResultItemDto item = mapPackerResultItemDto(placement, stackValue);

            int hash = item.hashCode();
            if (!itemMap.containsKey(hash)) {
                itemMap.put(hash, new PackerResultItemPairDto(item, 1));
            } else {
                PackerResultItemPairDto itemPair = itemMap.get(hash);
                itemPair.setQuantity(itemPair.getQuantity() + 1);
            }
        });

        return itemMap;
    }

    public PackerResultItemPairDto.PackerResultItemDto mapPackerResultItemDto(StackPlacement placement, StackValue stackValue) {
        return new PackerResultItemPairDto.PackerResultItemDto(
                placement.getStackable().getDescription(),
                new PackingJobResponseDto.DimensionsResponseDto(
                        this.packerProperties.getSystemLengthUnit(),
                        stackValue.getDx(),
                        stackValue.getDy(),
                        stackValue.getDz()
                )
        );
    }

    // TODO - Await update of library to fix volume calculation bug
    public PackingJobResponseDto.PackingJobVolumeDto mapPackingJobVolumeDto(List<Container> result) {
        AtomicLong totalVolume = new AtomicLong();
        AtomicLong totalVolumeRemaining = new AtomicLong();
        AtomicLong totalVolumeUsed = new AtomicLong();

        result.forEach(container -> {
            totalVolume.addAndGet(container.getVolume());
            totalVolumeRemaining.addAndGet(container.getStack().getFreeVolumeLoad()); // Bug in library, gets casts to Integer and can overflow
            totalVolumeUsed.addAndGet(container.getLoadVolume());
        });

        log.debug("Volume calculation successful: Volume[total: {}, used: {}, remaining: {}]", totalVolume.get(), totalVolumeUsed.get(), totalVolumeRemaining.get());

        return new PackingJobResponseDto.PackingJobVolumeDto(
                totalVolume.get(),
                totalVolumeRemaining.get(),
                totalVolumeUsed.get(),
                VolumeUnit.getVolumeByLength(this.packerProperties.getSystemLengthUnit())
        );
    }

}
