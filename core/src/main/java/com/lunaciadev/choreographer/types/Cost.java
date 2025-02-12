package com.lunaciadev.choreographer.types;

import com.badlogic.gdx.math.MathUtils;

public class Cost {
    private int bmatCost;
    private int ematCost;
    private int hematCost;
    private int rmatCost;

    public Cost(int bmatCost, int ematCost, int rmatCost, int hematCost) {
        this.bmatCost = bmatCost;
        this.ematCost = ematCost;
        this.hematCost = hematCost;
        this.rmatCost = rmatCost;
    }

    public int getBmatCost() {
        return bmatCost;
    }

    public int getEmatCost() {
        return ematCost;
    }

    public int getHematCost() {
        return hematCost;
    }

    public int getRmatCost() {
        return rmatCost;
    }

    /**
     * Add the other {@link Cost} into itself.
     * 
     * @param cost The other cost to add
     */
    public void add(Cost cost) {
        this.bmatCost += cost.bmatCost;
        this.ematCost += cost.ematCost;
        this.hematCost += cost.hematCost;
        this.rmatCost += cost.rmatCost;
    }

    /**
     * Subtract the other {@link Cost} from itself.
     * 
     * @param cost The other cost to subtract
     */
    public void subtract(Cost cost) {
        this.bmatCost -= cost.bmatCost;
        this.ematCost -= cost.ematCost;
        this.hematCost -= cost.hematCost;
        this.rmatCost -= cost.rmatCost;
    }

    /**
     * Convenience method to check if two {@link Cost} are equals in content.
     * 
     * @param cost The other cost to compare with
     * @return true if both cost are equal in content, false otherwise.
     */
    public boolean equals(Cost cost) {
        return this.bmatCost == cost.bmatCost & 
               this.ematCost == cost.ematCost &
               this.hematCost == cost.hematCost &
               this.rmatCost == cost.rmatCost;
    }

    /**
     * Get how many slot the cost is taking up.
     * 
     * A Slot in Foxhole hold 100 unit of the same item, so we simply divide each
     * cost individually by 100, rounded up. Adding those up together yield how many
     * slot is currently in use.
     * 
     * This function is used to check if we can add another item to the truck, hence
     * the input of otherCost. Passing an empty Cost work if you are only checking
     * the current slot usage.
     * 
     * @param otherCost The resource cost being added
     * @return How many slot is being used, with otherCost being considered.
     */
    public int getSlotNeeded(Cost otherCost) {
        int bmatCost = this.bmatCost + otherCost.bmatCost;
        int ematCost = this.ematCost + otherCost.ematCost;
        int rmatCost = this.rmatCost + otherCost.rmatCost;
        int hematCost = this.hematCost + otherCost.hematCost;

        return MathUtils.ceil(bmatCost / 100) + MathUtils.ceil(ematCost / 100) + MathUtils.ceil(hematCost / 100)
                + MathUtils.ceil(rmatCost / 100);
    }
}
