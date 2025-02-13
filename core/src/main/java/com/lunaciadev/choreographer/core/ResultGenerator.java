package com.lunaciadev.choreographer.core;

import java.util.HashMap;

import com.lunaciadev.choreographer.data.ItemData;
import com.lunaciadev.choreographer.types.Crate;

public class ResultGenerator {
    private HashMap<Integer, Crate> result;
    private ItemData itemData;

    public ResultGenerator(ItemData itemData) {
        this.itemData = itemData;
    }

    public void getResult(HashMap<Integer, Crate> result) {
        this.result = result;
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
