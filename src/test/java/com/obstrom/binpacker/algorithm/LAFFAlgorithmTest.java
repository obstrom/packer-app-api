package com.obstrom.binpacker.algorithm;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LAFFAlgorithmTest {

    @Test
    void findLongestTwoEdges() {
    }

    @Test
    void whenFindingLongestTwoEdges_givenBoxes_thenShouldBeCorrect() {
        // given
        List<Box> boxes = List.of(
                new Box(5, 7, 3),
                new Box(9, 18, 2),
                new Box(5, 17, 12),
                new Box(11, 12, 9),
                new Box(4, 20, 3)
        );

        // when
        LAFFAlgorithm algo = new LAFFAlgorithm();
        List<Integer> longestTwoEdges = algo.findLongestTwoEdgesAmongBoxes(boxes);

        // then
        assertEquals(longestTwoEdges.get(0), 20);
        assertEquals(longestTwoEdges.get(1), 18);
    }

}