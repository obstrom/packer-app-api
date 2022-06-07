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
    private StopWatch stopWatch = null;

    private boolean isInitialized = false;
    private boolean isFinished = false;

    private List<Container> result = List.of();

    public void init() {
        if (containers.isEmpty()) throw new PackagerException("No containers, can't initialize!");
        if (isInitialized)
            throw new PackagerException("Packager is already initialized. Create a new packager instead.");

        containers.sort(Comparator.comparingLong(Container::getVolume));

        this.packager = LargestAreaFitFirstPackager.newBuilder()
                .withContainers(containers)
                .build();

        this.isInitialized = true;
        log.debug("Packer for job initialized");
    }

    public PackingResults pack() {
        if (!isInitialized) throw new PackagerException("Packager is not initialized. Initialize the packer first.");
        if (isFinished) return new PackingResults(0L, result);

        runNewStopWatch();

        List<Container> packagerResult = packager.packList(products, 5, getDeadline());
        this.result = (packagerResult == null) ? List.of() : packagerResult; // Switches null value for empty list

        long taskTimeMillis = haltStopWatch();
        if (taskTimeMillis > timeoutMilliseconds)
            throw new JobTimeoutException("Job reached timeout limit of %s milliseconds.".formatted(timeoutMilliseconds));

        this.isFinished = true;
        log.debug("Packing for job completed");
        return new PackingResults(taskTimeMillis, result);
    }

    public void addContainer(Container container) {
        if (isInitialized)
            throw new PackagerException("Packager is already initialized with given containers. Create a new packager instead.");

        containers.add(container);
        containers.sort(Comparator.comparingLong(Container::getVolume));
        log.debug("container added to packer: {}", container.toString());
    }

    public void addProduct(StackableItem stackableItem) {
        products.add(stackableItem);
        log.debug("product added to packer: {}", stackableItem.toString());
    }

    public void addProducts(List<StackableItem> stackableItems) {
        products.addAll(stackableItems);
        log.debug("{} products added to packer", stackableItems.size());
    }

    private Long getDeadline() {
        return System.currentTimeMillis() + timeoutMilliseconds;
    }

    private void runNewStopWatch() {
        this.stopWatch = new StopWatch();
        log.debug("Packing job timer started");
        stopWatch.start();
    }

    private long haltStopWatch() {
        if (stopWatch == null)
            throw new IllegalStateException("Attempting to halt non-existent StopWatch!");

        stopWatch.stop();
        log.debug("Packing job timer stopped");
        return stopWatch.getLastTaskTimeMillis() == 0 ? 1 : stopWatch.getLastTaskTimeMillis();
    }

}
