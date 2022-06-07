package com.obstrom.packerservice.packer;

import com.github.skjolber.packing.api.Container;

import java.util.List;

public record PackingResults(
        Long runtimeMilliseconds,
        List<Container> resultsContainers
) {
}
