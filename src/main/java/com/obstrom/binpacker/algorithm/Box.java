package com.obstrom.binpacker.algorithm;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@ToString
public class Box {

    private final List<Integer> sides;
    private final int volume;

    public Box(int width, int depth, int height) {
        this.sides = new ArrayList<>(List.of(width, depth, height));
        this.sides.sort(Comparator.reverseOrder()); // Sort sides by size
        this.volume = calculateVolume(width, depth, height);
    }

    private int calculateVolume(int width, int depth, int height) {
        return width * depth * height;
    }

}
