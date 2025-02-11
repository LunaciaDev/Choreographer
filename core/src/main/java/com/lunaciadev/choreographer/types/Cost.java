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
     * Increase the amount of bmat needed by cost.
     * 
     * @param cost
     */
    public void increaseBmatCost(int cost) {
        this.bmatCost += cost;
    }

    /**
     * Increase the amount of emat needed by cost.
     * 
     * @param cost
     */
    public void increaseEmatCost(int cost) {
        this.ematCost += cost;
    }

    /**
     * Increase the amount of hemat needed by cost.
     * 
     * @param cost
     */
    public void increaseHematCost(int cost) {
        this.hematCost += cost;
    }

    /**
     * Increase the amount of rmat needed by cost.
     * 
     * @param cost
     */
    public void increaseRmatCost(int cost) {
        this.rmatCost += cost;
    }

    public int getSlotNeeded(Cost otherCost) {
        int bmatCost = this.bmatCost + otherCost.bmatCost;
        int ematCost = this.ematCost + otherCost.ematCost;
        int rmatCost = this.rmatCost + otherCost.rmatCost;
        int hematCost = this.hematCost + otherCost.hematCost;

        return MathUtils.ceil(bmatCost / 100) + MathUtils.ceil(ematCost / 100) + MathUtils.ceil(hematCost / 100)
                + MathUtils.ceil(rmatCost / 100);
    }
}
