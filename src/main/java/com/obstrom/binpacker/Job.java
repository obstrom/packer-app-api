package com.obstrom.binpacker;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
public class Job {

    private final UUID id;
    private final List<ItemSet> items;
    private final List<Item> bins;

    public Job() {
        this.id = UUID.randomUUID();
        this.items = new LinkedList<>();
        this.bins = new LinkedList<>();
    }

    public void addItem(Item item, int quantity) {
        this.items.add(new ItemSet(quantity, item));
    }

    public void addBin(Item bin) {
        this.bins.add(bin);
    }

}
