package com.obstrom.binpacker.algorithm;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoxTest {

    @Test
    public void whenCreatingBox_givenSides_shouldBeCorrect() {
        // given
        List<Integer> expectedSides = List.of(15, 10, 5);

        // then
        Box box = new Box(10, 5, 15);

        // should
        assertEquals(box.getSides(), expectedSides);
        assertEquals(box.getSides().get(0), 15);
        assertEquals(box.getSides().get(1), 10);
        assertEquals(box.getSides().get(2), 5);
    }

    @Test
    public void whenCalculatingVolume_givenSides_shouldBeCorrect() {
        // given
        int expectedVolume = 750;

        // then
        Box box = new Box(5, 10, 15);

        // should
        assertEquals(box.getVolume(), expectedVolume);
    }

}