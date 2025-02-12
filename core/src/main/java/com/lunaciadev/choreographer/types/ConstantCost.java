package com.lunaciadev.choreographer.types;

// make it easy to cast from ConstantCost to Cost.
public class ConstantCost extends Cost {
    public ConstantCost(int bmatCost, int ematCost, int rmatCost, int hematCost) {
        super(bmatCost, ematCost, rmatCost, hematCost);
    }

    @Override
    public void add(Cost cost) {
        throw new UnsupportedOperationException("Cannot add to a ConstantCost.");
    }

    @Override
    public void subtract(Cost cost) {
        throw new UnsupportedOperationException("Cannot subtract from a ConstantCost.");
    }
}
