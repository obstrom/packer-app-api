package com.obstrom.packerservice.service;

import com.github.skjolber.packing.api.Container;
import com.github.skjolber.packing.api.StackableItem;
import com.obstrom.packerservice.dto.PackingJobRequestDto;
import com.obstrom.packerservice.dto.PackingJobResponseDto;
import com.obstrom.packerservice.packer.PackingResults;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class RequestService {

    private final DtoService dtoService;
    private final PackingService packingService;

    public PackingJobResponseDto handlePackingJobRequest(PackingJobRequestDto packingJobRequestDto) {
        String jobId = handleNoJobId(packingJobRequestDto.jobId());

        log.info("Handling new packing request {}", jobId);

        List<StackableItem> products = dtoService.mapStackableItemList(packingJobRequestDto.products());
        List<Container> containers = dtoService.mapContainerList(packingJobRequestDto.boxes());
        PackingResults packingResults = packingService.pack(jobId, containers, products);

        return dtoService.mapPackingJobResponseDto(packingResults, packingJobRequestDto.visualizer());
    }

    private String handleNoJobId(UUID jobId) {
        return jobId == null ? UUID.randomUUID().toString() : jobId.toString();
    }

}
