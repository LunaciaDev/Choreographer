package com.lunaciadev.choreographer.types;

import java.util.List;

import com.lunaciadev.choreographer.data.ItemData;

public class Truck {
    private List<Integer> itemIDs;
    private Cost truckCost;
    private ItemData itemData;

    public Truck(ItemData itemData) {
        this.itemData = itemData;
        this.truckCost = new Cost(0, 0, 0, 0);
    }

    public boolean addItem(int id) {
        Cost itemCost = itemData.getCost(id);

        if (truckCost.getSlotNeeded(itemCost) > 15) {
            return false;
        }

        truckCost.increaseBmatCost(itemCost.getBmatCost());
        truckCost.increaseEmatCost(itemCost.getEmatCost());
        truckCost.increaseHematCost(itemCost.getHematCost());
        truckCost.increaseRmatCost(itemCost.getRmatCost());

        return true;
    }

    public Integer[] getTruckContent() {
        return (Integer[]) itemIDs.toArray();
    }
}
