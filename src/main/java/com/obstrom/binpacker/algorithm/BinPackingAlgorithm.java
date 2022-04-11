package com.obstrom.binpacker.algorithm;

import java.util.List;

public interface BinPackingAlgorithm {

    /* Possible algorithms to explore:
     * - Guillotine
     * - Shelf
     * - LAFF
     */

    void initialize(List<Box> inputBoxes);

    void run();

}
