package com.lunaciadev.choreographer.types;

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
}
