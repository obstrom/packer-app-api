package com.obstrom.binpacker.algorithm;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 3D Bin Packing algorithm implementation using a "largest area fit-first" (LAFF) algorithm derived
 * from the paper <a href="https://www.parkbeachsystems.com/images/usps/An_Efficient_Algorithm_for_3D_Rectangular_Box_Packing.pdf">An Efficient Algorithm for 3D Rectangular Box Packing</a>.
 */
public class LAFFAlgorithm implements BinPackingAlgorithm {

    // Output
    //  - Volume of container
    //  - The used space
    //  - Wasted space
    //  - Running time

    // Steps
    //  1. Items to place
    //  2. Determine the width and depth the Bin
    //  3. Select the Item that has the widest surface area,
    //      preferring the one with the least height
    //  3.1 Determine the height of container and decrement the number of items.
    //  3.2 Terminate if no more Items.
    //  3.3 If the space left on this level is 0 (?), then go back to step 3 again
    //      Else Chose the box that fits into this space:
    //        - If there is none go back to step 3 again
    //        - Else if there is more than one, pick the one with the biggest volume
    //  3.3.1 Determine the dimensions of the space (see formula)
    //  3.3.2 Decrement the number of Items left
    //  3.3.3 If the number of Items is 0 then terminate, otherwise go back to step 3.3 again

    // Variables
    // aN = width, bN = depth, cN = height, kN = quantity where N is a unique item size
    // k = the Bin
    // i = the Item we are working with right now

    // ak (width of Bin) = First longest edge of all Items (CONSTANT)
    // bk (depth of Bin) = Second longest edge of all Items (CONSTANT)
    // ck (height of Bin) = 0

    // ck (height of Bin) = ck + ci (height of this layer)
    // ki (Items left?) ki--

    // space left width-wise = ak (width of Bin) - ai (width of layer)
    // space left depth-wise = bk (depth of Bin) - bi (depth of layer)

    private List<Box> inputBoxes;
    private boolean isInitialized = false;

    private int longestEdge = 0; // ak
    private int secondLongestEdge = 0; // bk
    private int containerHeight = 0; // ck
    private int containerBaseArea = 0; // ak * bk

    // TODO
    //  - Add running time counter

    @Override
    public void initialize(List<Box> inputBoxes) {
        this.inputBoxes = inputBoxes;
        this.isInitialized = true;
    }

    @Override
    public void run() {
        if (!isInitialized)
            throw new IllegalStateException("Attempted to run LAFFAlgorithm before it was initialized!");

        // Step 1
        determineContainerBase();

        // Step 2
        placeLargestBoxFirst();


        determineHeightOfContainer();
    }

    public void determineContainerBase() {
        List<Integer> longestTwoEdges = findLongestTwoEdgesAmongBoxes(this.inputBoxes);
        longestEdge = longestTwoEdges.get(0);
        secondLongestEdge = longestTwoEdges.get(1);
        containerBaseArea = longestEdge * secondLongestEdge;
    }

    public List<Integer> findLongestTwoEdgesAmongBoxes(List<Box> boxes) {
        return boxes.stream()
                .flatMap(x -> x.getSides().stream())
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList())
                .subList(0, 2);
    }

    public void placeLargestBoxFirst() {
        /*
        Choose the box which has the widest surface area.
        If there is more than one, chose the box which has minimum height.
        Place this box (ith) on the largest surface parallel to the base of container.
         */
        findBoxWithLargestFaceArea(this.inputBoxes);
    }

    public Box findBoxWithLargestFaceArea(List<Box> boxes) {
        /*
        Choose the box which has the widest surface area.
        If there is more than one, chose the box which has minimum height.
         */

        Box foundBox = null;
        int largestArea = 0;

        for (Box box : boxes) {
            // Box sides already sorted by size, first side will always be largest
            int boxFaceArea = box.getSides().get(0) * box.getSides().get(1);
            if (
                    (boxFaceArea >= largestArea)
                            && (true/*foundBox == null || foundBox.getSides().get(3)*/)
            ) {
                foundBox = box;
                largestArea = boxFaceArea;
            }
        }

        return foundBox;
    }

    public void determineHeightOfContainer() {
        /* Determine the height of container and decrement the number of ith box.
        ck=ck+ ci
        ki=ki-1
         if the number of Boxes is zero then terminate
         */
    }

}
