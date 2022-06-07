package com.obstrom.packerservice.core.packer;

import com.github.skjolber.packing.api.Box;
import com.github.skjolber.packing.api.Container;
import com.github.skjolber.packing.api.StackableItem;
import com.github.skjolber.packing.packer.laff.LargestAreaFitFirstPackager;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PackagerTest {

    @Test
    void testLibraryBasicSetupWorks() {
        // given
        Container container = Container.newBuilder()
                .withDescription("1")
                .withSize(10, 10, 3)
                .withEmptyWeight(1)
                .withMaxLoadWeight(100)
                .build();

        LargestAreaFitFirstPackager packager = LargestAreaFitFirstPackager.newBuilder()
                .withContainers(List.of(container))
                .build();

        List<StackableItem> products = List.of(
                new StackableItem(Box.newBuilder().withId("foo").withSize(6, 10, 2).withRotate3D().withWeight(25).build(), 1)
        );

        // when
        Container match = packager.pack(products);

        // then
        assertNotNull(match);
    }

    @Test
    void testLibraryBasicSetupWorksMultiple() {
        // given
        List<Container> containers = List.of(Container.newBuilder()
                .withDescription("1")
                .withSize(10, 10, 3)
                .withEmptyWeight(1)
                .withMaxLoadWeight(100)
                .build());

        LargestAreaFitFirstPackager packager = LargestAreaFitFirstPackager.newBuilder()
                .withContainers(containers)
                .build();

        List<StackableItem> products = List.of(
                new StackableItem(Box.newBuilder().withId("foo").withSize(6, 10, 2).withRotate3D().withWeight(25).build(), 1)
        );

        // when
        List<Container> matches = packager.packList(products, 1);

        // then
        assertNotNull(matches);
    }


    @Test
    @Disabled
    void givenValidBasicContainersAndProducts_whenPackagerPacks_thenFindCorrectContainer() {
        // TODO - Update broken test

        // given
        String jobId = UUID.randomUUID().toString();
        List<Container> containers = List.of(
                Container.newBuilder()
                        .withDescription("Test container 1")
                        .withSize(50, 50, 50)
                        .withEmptyWeight(1)
                        .withMaxLoadWeight(999)
                        .build(),
                Container.newBuilder()
                        .withDescription("Test container 2")
                        .withSize(60, 60, 60)
                        .withEmptyWeight(1)
                        .withMaxLoadWeight(999)
                        .build()
        );
        StackableItem stackableItem = new StackableItem(
                Box.newBuilder()
                        .withDescription("Test box variation 1")
                        .withId("test1")
                        .withSize(50, 50, 50)
                        .withRotate3D()
                        .withWeight(1)
                        .build(),
                1
        );

        // when
        Packager packager = new Packager(jobId, 1000, containers);
        packager.init();
        packager.addProduct(stackableItem);
        PackingResults results = packager.pack();
        List<Container> resultContainers = results.resultsContainers();

        // then
        assertFalse(resultContainers.isEmpty());
        assertEquals(resultContainers.size(), 1);
        assertEquals(resultContainers.get(0).getDescription(), "Test container 1");
    }

}