package com.obstrom.packerservice.core.packer;

import com.github.skjolber.packing.api.Box;
import com.github.skjolber.packing.api.Container;
import com.github.skjolber.packing.api.StackableItem;
import com.github.skjolber.packing.packer.PackagerException;
import com.obstrom.packerservice.api.exception.JobTimeoutException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PackagerTest {

    @Test
    void givenValidProductAndContainer_whenPacking_thenSucceed() {
        // given
        String jobId = UUID.randomUUID().toString();
        long timeoutMs = 5000L;
        Container container = Container.newBuilder()
                .withDescription("Test container")
                .withSize(100, 100, 100)
                .withEmptyWeight(10)
                .withMaxLoadWeight(9999)
                .build();
        StackableItem product = new StackableItem(Box.newBuilder()
                .withDescription("Test product")
                .withSize(20, 20, 20)
                .withWeight(200)
                .withRotate3D()
                .build(), 4);

        // when
        Packager packager = new Packager(jobId, timeoutMs);
        packager.addContainer(container);
        packager.init();
        packager.addProduct(product);
        PackingResults results = packager.pack();

        // then
        assertEquals(results.resultsContainers().size(), 1);
    }

    @Test
    void givenMultipleValidProductsAndContainers_whenPacking_thenSucceed() {
        // given
        String jobId = UUID.randomUUID().toString();
        long timeoutMs = 5000L;

        List<Container> containers = List.of(
                Container.newBuilder()
                        .withDescription("Test container 1")
                        .withSize(50, 50, 50)
                        .withEmptyWeight(5)
                        .withMaxLoadWeight(9999)
                        .build(),
                Container.newBuilder()
                        .withDescription("Test container 2")
                        .withSize(25, 25, 25)
                        .withEmptyWeight(3)
                        .withMaxLoadWeight(9999)
                        .build()
        );

        List<StackableItem> products = List.of(
                new StackableItem(Box.newBuilder()
                        .withDescription("Test product 1")
                        .withSize(20, 20, 20)
                        .withWeight(200)
                        .withRotate3D()
                        .build(), 6),
                new StackableItem(Box.newBuilder()
                        .withDescription("Test product 2")
                        .withSize(15, 15, 40)
                        .withWeight(100)
                        .withRotate3D()
                        .build(), 8),
                new StackableItem(Box.newBuilder()
                        .withDescription("Test product 3")
                        .withSize(5, 5, 5)
                        .withWeight(50)
                        .withRotate3D()
                        .build(), 15)
        );

        // when
        Packager packager = new Packager(jobId, timeoutMs);
        packager.addContainers(containers);
        packager.init();
        packager.addProducts(products);
        PackingResults results = packager.pack();

        // then
        assertEquals(results.resultsContainers().size(), 2);
    }

    @Test
    void whenPackingWithoutContainers_thenShouldThrowPackingException() {
        // given
        String jobId = UUID.randomUUID().toString();
        long timeoutMs = 5000L;

        // when
        Packager packager = new Packager(jobId, timeoutMs);

        // then
        PackagerException thrown = assertThrows(PackagerException.class, packager::init);
        assertEquals("No containers, can't initialize for job '%s'!".formatted(jobId), thrown.getMessage());
    }

    @Test
    void whenPackingWithoutProducts_thenShouldThrowPackingException() {
        // given
        String jobId = UUID.randomUUID().toString();
        long timeoutMs = 5000L;

        List<Container> containers = List.of(
                Container.newBuilder()
                        .withDescription("Test container 1")
                        .withSize(50, 50, 50)
                        .withEmptyWeight(5)
                        .withMaxLoadWeight(9999)
                        .build(),
                Container.newBuilder()
                        .withDescription("Test container 2")
                        .withSize(25, 25, 25)
                        .withEmptyWeight(3)
                        .withMaxLoadWeight(9999)
                        .build()
        );

        // when
        Packager packager = new Packager(jobId, timeoutMs, containers);
        packager.init();

        // then
        PackagerException thrown = assertThrows(PackagerException.class, packager::pack);
        assertEquals("Packager '%s' has no products to pack!".formatted(jobId), thrown.getMessage());
    }

    @Test
    void whenPackingWithoutInitializing_thenShouldThrowPackingException() {
        // given
        String jobId = UUID.randomUUID().toString();
        long timeoutMs = 5000L;

        // when
        Packager packager = new Packager(jobId, timeoutMs);

        // then
        PackagerException thrown = assertThrows(PackagerException.class, packager::pack);
        assertEquals("Packager '%s' is not initialized. Initialize the packer first.".formatted(jobId), thrown.getMessage());
    }

    @Test
    void whenInitializingPackerAgain_thenShouldThrowPackingException() {
        // given
        String jobId = UUID.randomUUID().toString();
        long timeoutMs = 5000L;

        List<Container> containers = List.of(
                Container.newBuilder()
                        .withDescription("Test container 1")
                        .withSize(50, 50, 50)
                        .withEmptyWeight(5)
                        .withMaxLoadWeight(9999)
                        .build()
        );

        // when
        Packager packager = new Packager(jobId, timeoutMs, containers);
        packager.init();

        // then
        PackagerException thrown = assertThrows(PackagerException.class, packager::init);
        assertEquals("Packager '%s' is already initialized. Create a new packager instead.".formatted(jobId), thrown.getMessage());
    }

    @Test
    void givenInitializedPacker_whenAddingMoreContainers_thenShouldThrowPackingException() {
        // given
        String jobId = UUID.randomUUID().toString();
        long timeoutMs = 5000L;

        Container container = Container.newBuilder()
                .withDescription("Test container 1")
                .withSize(50, 50, 50)
                .withEmptyWeight(5)
                .withMaxLoadWeight(9999)
                .build();

        // when
        Packager packager = new Packager(jobId, timeoutMs, List.of(container));
        packager.init();

        // then
        PackagerException containerThrown = assertThrows(PackagerException.class, () -> packager.addContainer(container));
        assertEquals("Packager '%s' is already initialized with containers. Create a new packager instead.".formatted(jobId), containerThrown.getMessage());
        PackagerException containersThrown = assertThrows(PackagerException.class, () -> packager.addContainers(List.of(container)));
        assertEquals("Packager '%s' is already initialized with containers. Create a new packager instead.".formatted(jobId), containersThrown.getMessage());
    }

    @Test
    void whenExceedingTimeout_thenShouldThrowJobTimeoutException() {
        // given
        String jobId = UUID.randomUUID().toString();
        long timeoutMs = 10L;
        Container container = Container.newBuilder()
                .withDescription("Test container")
                .withSize(1000, 1000, 1000)
                .withEmptyWeight(10)
                .withMaxLoadWeight(9999)
                .build();
        StackableItem product = new StackableItem(Box.newBuilder()
                .withDescription("Test product")
                .withSize(1, 1, 1)
                .withWeight(1)
                .withRotate3D()
                .build(), 10000000);

        // when
        Packager packager = new Packager(jobId, timeoutMs);
        packager.addContainer(container);
        packager.init();
        packager.addProduct(product);

        // then
        JobTimeoutException thrown = assertThrows(JobTimeoutException.class, packager::pack);
        assertEquals("Job '%s' reached timeout limit of %s milliseconds.".formatted(jobId, timeoutMs), thrown.getMessage());
    }

}