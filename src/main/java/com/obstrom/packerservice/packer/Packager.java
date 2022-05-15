package com.obstrom.packerservice.packer;

import com.github.skjolber.packing.api.Container;
import com.github.skjolber.packing.api.StackableItem;
import com.github.skjolber.packing.packer.PackagerException;
import com.github.skjolber.packing.packer.laff.LargestAreaFitFirstPackager;
import com.obstrom.packerservice.exception.JobTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class Packager {

    private final long timeoutMilliseconds;
    private final List<Container> containers;

    private final List<StackableItem> products = new ArrayList<>();
    private LargestAreaFitFirstPackager packager = null;

    private boolean isInitialized = false;
    private boolean isFinished = false;

    private List<Container> result = List.of();

    public void init() {
        if (containers.isEmpty()) throw new PackagerException("No containers, can't initialize!");
        if (isInitialized)
            throw new PackagerException("Packager is already initialized. Create a new packager instead.");

        containers.sort(Comparator.comparingLong(Container::getVolume));

        packager = LargestAreaFitFirstPackager.newBuilder()
                .withContainers(containers)
                .build();

        isInitialized = true;
    }

    public void addContainer(Container container) {
        if (isInitialized)
            throw new PackagerException("Packager is already initialized with given containers. Create a new packager instead.");
        containers.add(container);
        containers.sort(Comparator.comparingLong(Container::getVolume));
    }

    public void addProduct(StackableItem stackableItem) {
        products.add(stackableItem);
    }

    public void addProducts(List<StackableItem> stackableItems) {
        products.addAll(stackableItems);
    }

    public List<Container> pack() {
        if (!isInitialized) throw new PackagerException("Packager is not initialized. Initialize the packer first.");
        if (isFinished) return result;

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<Container> packagerResult = packager.packList(products, 5, getDeadline());
        result = (packagerResult == null) ? List.of() : packagerResult; // Switches null value for empty list

        stopWatch.stop();
        log.info("Packing job finished in {} ms", stopWatch.getLastTaskTimeMillis());

        if (stopWatch.getLastTaskTimeMillis() > timeoutMilliseconds)
            throw new JobTimeoutException("Job reached timeout limit of %s milliseconds.".formatted(timeoutMilliseconds));

        isFinished = true;
        return result;
    }

    private Long getDeadline() {
        return System.currentTimeMillis() + timeoutMilliseconds;
    }

}
