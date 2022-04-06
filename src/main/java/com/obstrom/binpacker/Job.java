package com.obstrom.binpacker;

import com.obstrom.binpacker.exception.JobException;
import com.obstrom.binpacker.item.Item;
import com.obstrom.binpacker.item.ItemSet;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
public class Job {

    // TODO
    //  - Make runnable?

    private final UUID id;
    private final List<ItemSet> items;
    private final List<Item> bins;
    private Object result;

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

    public void run() {
        if (items.size() == 0)
            throw new JobException("Can not run job without any items");

        if (bins.size() == 0)
            throw new JobException("Can not run job without any bins");

        // TODO - Run algorithm and set result
    }

    public Object getResult() {
        if (result == null) this.run();
        return this.result;
    }

}
