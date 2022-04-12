package com.obstrom.packerservice.controller;

import com.obstrom.packerservice.dto.PackingJobRequestDto;
import com.obstrom.packerservice.service.PackingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("api/v1/packer")
@AllArgsConstructor
public class PackageServiceController {

    private final PackingService packingService;

    @PostMapping("pack")
    private Mono<Object> pack(@RequestBody @Valid PackingJobRequestDto requestDto) {
        packingService.handlePackingJobRequest(requestDto);

        return null;
    }

}
