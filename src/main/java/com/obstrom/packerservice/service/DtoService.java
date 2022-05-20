package com.obstrom.packerservice.service;

import com.github.skjolber.packing.api.*;
import com.github.skjolber.packing.visualizer.api.packaging.PackagingResultVisualizer;
import com.obstrom.packerservice.config.PackerProperties;
import com.obstrom.packerservice.dto.*;
import com.obstrom.packerservice.packer.Packager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DtoService {

    private final PackerProperties packerProperties;
    private final PackingService packingService;

    public PackingJobResponseDto handlePackingJobRequest(PackingJobRequestDto packingJobRequestDto) {
        List<StackableItem> products = mapItemRequestDtoToStackableItems(packingJobRequestDto.getProducts());
        List<Container> containers = mapItemRequestDtoToContainers(packingJobRequestDto.getBoxes());

        Packager packager = new Packager(this.packerProperties.getTimeoutMilliseconds(), containers);
        packager.init();
        packager.addProducts(products);
        Packager.PackingResults result = packager.pack();

        return mapPackingResultsIntoResponseDto(result);
    }

    private PackingJobResponseDto mapPackingResultsIntoResponseDto(Packager.PackingResults result) {
        List<Container> resultsContainers = result.getResultsContainers();

        List<PackingJobResponseDto.ContainerResponseDto> resultContainers = resultsContainers.stream()
                .map(container -> new PackingJobResponseDto.ContainerResponseDto(
                        container.getId(),
                        container.getDescription(),
                        container.getVolume(),
                        packingService.calculateUsedVolumePercentage(container.getVolume(), container.getStack().getFreeVolumeLoad()),
                        container.getWeight(),
                        stackToResponseDto(container.getStack()),
                        stackValuesToResponseDto(container.getStackValues()),
                        stackPlacementsToResponseDto(container.getStack().getPlacements())
                ))
                .collect(Collectors.toList());

        PackingJobResponseDto.PackingJobVolumeDto packingJobVolumeDto = packingService.calculateJobVolumeDto(resultsContainers);
        Integer totalWeight = packingService.calculateJobTotalWeight(resultsContainers);

        PackagingResultVisualizer visualizeData = packingService.generateVisualizerData(resultsContainers);

        return new PackingJobResponseDto(
                packingJobVolumeDto,
                totalWeight,
                result.getRuntimeMilliseconds(),
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
                    new PackingJobResponseDto.DimensionsResponseDto(stackValue.getDx(), stackValue.getDy(), stackValue.getDz())
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
                        itemPair.getItem().getDescription(),
                        itemPair.getItem().getDimensions(),
                        itemPair.getQuantity()
                ))
                .collect(Collectors.toList());
    }

}
