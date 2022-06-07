package com.obstrom.packerservice.service;

import com.github.skjolber.packing.api.*;
import com.github.skjolber.packing.visualizer.api.packaging.PackagingResultVisualizer;
import com.obstrom.packerservice.config.PackerProperties;
import com.obstrom.packerservice.dto.*;
import com.obstrom.packerservice.packer.PackingResults;
import com.obstrom.packerservice.units.StandardUnitsUtil;
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

    public PackingJobResponseDto handlePackingJobRequest(PackingJobRequestDto packingJobRequestDto) {
        List<StackableItem> products = mapItemRequestDtoToStackableItems(packingJobRequestDto.products());
        List<Container> containers = mapItemRequestDtoToContainers(packingJobRequestDto.boxes());

        PackingResults packingResults = packingService.pack(containers, products);

        return mapPackingResultsIntoResponseDto(packingResults, packingJobRequestDto.visualizer());
    }

    private PackingJobResponseDto mapPackingResultsIntoResponseDto(PackingResults result, boolean visualizer) {
        List<Container> resultsContainers = result.resultsContainers();

        List<PackingJobResponseDto.ContainerResponseDto> resultContainers = resultsContainers.stream()
                .map(container -> new PackingJobResponseDto.ContainerResponseDto(
                        container.getId(),
                        container.getDescription(),
                        container.getVolume(),
                        packingService.calculateUsedVolumePercentage(container.getVolume(), container.getStack().getFreeVolumeLoad()),
                        container.getWeight(),
                        this.packerProperties.getSystemWeightUnit(),
                        stackToResponseDto(container.getStack()),
                        stackValuesToResponseDto(container.getStackValues()),
                        stackPlacementsToResponseDto(container.getStack().getPlacements())
                ))
                .toList();

        PackingJobResponseDto.PackingJobVolumeDto packingJobVolumeDto = calculateJobVolumeDto(resultsContainers);
        Integer totalWeight = packingService.calculateJobTotalWeight(resultsContainers);

        PackagingResultVisualizer visualizeData = (visualizer) ? packingService.generateVisualizerData(resultsContainers) : null;

        return new PackingJobResponseDto(
                packingJobVolumeDto,
                totalWeight,
                result.runtimeMilliseconds(),
                resultContainers,
                visualizeData
        );
    }

    private List<StackableItem> mapItemRequestDtoToStackableItems(List<ItemRequestDto> items) {
        return items.stream()
                .map(dto -> new StackableItem(
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
                        dto.getQuantity()))
                .collect(Collectors.toList());
    }

    private List<Container> mapItemRequestDtoToContainers(List<ContainerRequestDto> items) {
        return items.stream()
                .map(dto -> packingService.containerBuilder(
                        dto.getId(),
                        dto.getDescription(),
                        dto.getWidth(),
                        dto.getDepth(),
                        dto.getHeight(),
                        dto.getWeight(),
                        dto.getMaxLoad(),
                        dto.getLengthUnitType(),
                        dto.getWeightUnitType()
                ))
                .collect(Collectors.toList());
    }

    private PackingJobResponseDto.ContainerDetailsResponseDto stackToResponseDto(Stack stack) {
        return new PackingJobResponseDto.ContainerDetailsResponseDto(
                stack.getSize(),
                stack.getFreeVolumeLoad(),
                stack.getFreeWeightLoad()
        );
    }

    private PackingJobResponseDto.DimensionsResponseDto stackValuesToResponseDto(ContainerStackValue[] values) {
        ContainerStackValue stackValue = Arrays.stream(values).findFirst()
                .orElseThrow(() -> new IllegalStateException("No container stack values in results!"));

        return new PackingJobResponseDto.DimensionsResponseDto(
                this.packerProperties.getSystemLengthUnit(),
                stackValue.getDx(),
                stackValue.getDy(),
                stackValue.getDz()
        );
    }

    private List<PackingJobResponseDto.ItemResponseDto> stackPlacementsToResponseDto(List<StackPlacement> placements) {
        HashMap<Integer, PackerResultItemPairDto> itemMap = new HashMap<>();

        placements.forEach(placement -> {
            StackValue stackValue = Arrays.stream(placement.getStackable().getStackValues()).findFirst()
                    .orElseThrow(() -> new IllegalStateException("No item stack values in results!"));

            PackerResultItemPairDto.PackerResultItemDto item = new PackerResultItemPairDto.PackerResultItemDto(
                    placement.getStackable().getDescription(),
                    new PackingJobResponseDto.DimensionsResponseDto(
                            this.packerProperties.getSystemLengthUnit(),
                            stackValue.getDx(),
                            stackValue.getDy(),
                            stackValue.getDz()
                    )
            );

            int hash = item.hashCode();
            if (!itemMap.containsKey(hash)) {
                itemMap.put(hash, new PackerResultItemPairDto(item, 1));
            } else {
                PackerResultItemPairDto itemPair = itemMap.get(hash);
                itemPair.setQuantity(itemPair.getQuantity() + 1);
            }
        });

        return itemMap.values().stream()
                .map(itemPair -> new PackingJobResponseDto.ItemResponseDto(
                        itemPair.getItem().description(),
                        itemPair.getItem().dimensions(),
                        itemPair.getQuantity()
                ))
                .collect(Collectors.toList());
    }

    // TODO - Await update of library to fix volume calculation bug
    private PackingJobResponseDto.PackingJobVolumeDto calculateJobVolumeDto(List<Container> result) {
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
                StandardUnitsUtil.Volume.getVolumeByLength(this.packerProperties.getSystemLengthUnit())
        );
    }

}
