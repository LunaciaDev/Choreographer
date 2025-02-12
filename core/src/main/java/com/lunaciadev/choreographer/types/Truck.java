package com.lunaciadev.choreographer.types;

import java.util.ArrayList;

import com.lunaciadev.choreographer.data.ItemData;

public class Truck {
    private ArrayList<Integer> truckContent;
    private Cost truckCost;
    private ItemData itemData;

    public Truck(ItemData itemData) {
        this.itemData = itemData;
        this.truckCost = new Cost(0, 0, 0, 0);
        this.truckContent = new ArrayList<Integer>();
    }

    public boolean addItem(int id) {
        Cost itemCost = itemData.getCost(id);

        if (truckCost.getSlotNeeded(itemCost) > 15) {
            return false;
        }

        truckCost.add(itemCost);
        truckContent.add(id);

        return true;
    }

    public boolean removeLastAdded() {
        if (truckContent.isEmpty()) {
            return false;
        }

        int id = truckContent.remove(truckContent.size() - 1);
        Cost itemCost = itemData.getCost(id);

        truckCost.subtract(itemCost);

        return true;
    }

    public Integer[] getTruckContent() {
        Integer[] content = new Integer[truckContent.size()];

        return truckContent.toArray(content);
    }

    public Cost getTruckCost() {
        return truckCost;
    }
}
