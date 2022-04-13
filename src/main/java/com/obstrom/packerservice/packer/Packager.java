package com.obstrom.packerservice.packer;

import com.github.skjolber.packing.api.Container;
import com.github.skjolber.packing.api.StackableItem;
import com.github.skjolber.packing.packer.PackagerException;
import com.github.skjolber.packing.packer.laff.LargestAreaFitFirstPackager;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Packager {

    private final long TIMEOUT_MILLISECONDS = 1000L;

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

        packager = LargestAreaFitFirstPackager.newBuilder()
                .withContainers(containers)
                .build();

        isInitialized = true;
    }

    public void addContainer(Container container) {
        if (isInitialized)
            throw new PackagerException("Packager is already initialized with given containers. Create a new packager instead.");
        containers.add(container);
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

        List<Container> packagerResult = packager.packList(products, 5, getDeadline());
        result = (packagerResult == null) ? List.of() : packagerResult; // Switches null value for empty list

        isFinished = true;
        return result;
    }

    private Long getDeadline() {
        return System.currentTimeMillis() + TIMEOUT_MILLISECONDS;
    }

}
