package com.obstrom.packerservice.api.controller;

import com.obstrom.packerservice.api.dto.PackingJobRequestDto;
import com.obstrom.packerservice.api.dto.PackingJobResponseDto;
import com.obstrom.packerservice.api.service.RequestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("api/v1/packer")
@AllArgsConstructor
public class PackageServiceController {

    private final RequestService requestService;

    @PostMapping("pack")
    private PackingJobResponseDto pack(@Valid @RequestBody PackingJobRequestDto requestDto) {
        return requestService.handlePackingJobRequest(requestDto);
    }

}
