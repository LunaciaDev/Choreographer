package com.lunaciadev.choreographer.utils;

import com.lunaciadev.choreographer.types.Cost;

public class CostStringGenerator {
    private boolean isFirstItem;
    private StringBuilder costString;

    public CostStringGenerator() {
        costString = new StringBuilder();
    }

    public String generate(Cost itemCost) {
        // reuse the same buffer
        costString.setLength(0);
        isFirstItem = true;

        addToString(itemCost.getBmatCost(), costString, 0);
        addToString(itemCost.getEmatCost(), costString, 1);
        addToString(itemCost.getHematCost(), costString, 2);
        addToString(itemCost.getRmatCost(), costString, 3);

        if (costString.length() == 0) {
            return "0b";
        }

        return costString.toString();
    }

    private void addToString(int cost, StringBuilder costString, int type) {
        if (cost == 0) return;

        if (!isFirstItem) {
            costString.append(", ");
        }

        costString.append(Integer.toString(cost));

        switch(type) {
            case 0: costString.append("b"); break;
            case 1: costString.append("e"); break;
            case 2: costString.append("he"); break;
            case 3: costString.append("r"); break;
        }

        isFirstItem = false;
    }

}
