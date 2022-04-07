package com.obstrom.binpacker.old;

import com.obstrom.binpacker.old.exception.JobException;
import com.obstrom.binpacker.old.item.Bin;
import com.obstrom.binpacker.old.item.Item;
import com.obstrom.binpacker.old.item.ItemSet;
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
    private final List<Bin> bins;
    private Object result;

    public Job() {
        this.id = UUID.randomUUID();
        this.items = new LinkedList<>();
        this.bins = new LinkedList<>();
    }

    public void addItem(Item item, int quantity) {
        this.items.add(new ItemSet(quantity, item));
    }

    public void addBin(Bin bin) {
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
