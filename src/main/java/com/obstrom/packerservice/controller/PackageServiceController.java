package com.obstrom.packerservice.controller;

import com.obstrom.packerservice.dto.PackingJobRequestDto;
import com.obstrom.packerservice.dto.PackingJobResponseDto;
import com.obstrom.packerservice.service.DtoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("api/v1/packer")
@AllArgsConstructor
public class PackageServiceController {

    private final DtoService dtoService;

    @PostMapping("pack")
    private PackingJobResponseDto pack(@Valid @RequestBody PackingJobRequestDto requestDto) {
        PackingJobResponseDto responseDto = dtoService.handlePackingJobRequest(requestDto);

        return responseDto;
    }

}
