package com.lunaciadev.choreographer.core;

import java.util.Arrays;
import java.util.HashMap;

import com.lunaciadev.choreographer.data.ItemData;
import com.lunaciadev.choreographer.types.Crate;

public class ResultGenerator {
    private HashMap<Integer, Crate> result;
    private Crate[] sortedResult;
    private ItemData itemData;

    public ResultGenerator(ItemData itemData) {
        this.itemData = itemData;
    }

    public void getResult(HashMap<Integer, Crate> result) {
        Integer[] sortedKeys = new Integer[result.size()];
        sortedResult = new Crate[result.size()];

        this.result = result;
        result.keySet().toArray(sortedKeys);
        Arrays.sort(sortedKeys);

        for (int i = 0; i < result.size(); i++) {
            sortedResult[i] = result.get(sortedKeys[i]);
        }
    }

    public Crate[] getSortedResult() {
        return sortedResult;
    }

    public String generateGoogleSheetPaste() {
        StringBuilder output = new StringBuilder();
        int maxID = itemData.getItemDataSize();

        for (int key = 0; key < maxID; key++) {
            if (result.containsKey(key)) {
                output.append(result.get(key).getQueueMade());
            }
            else {
                output.append(0);
            }

            if (key != maxID-1) {
                output.append("\t");
            }
        }

        return output.toString();
    }
}
