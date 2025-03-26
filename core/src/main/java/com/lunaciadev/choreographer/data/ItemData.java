package com.lunaciadev.choreographer.data;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.lunaciadev.choreographer.types.ConstantCost;
import com.lunaciadev.choreographer.types.QueueType;

public class ItemData {
    private class ItemDataHolder {
        private QueueType queueType;
        private String itemName;
        private ConstantCost cost;

        public ItemDataHolder(String itemName, QueueType queueType, ConstantCost cost) {
            this.itemName = itemName;
            this.queueType = queueType;
            this.cost = cost;
        }

        public String getItemName() {
            return itemName;
        }

        public ConstantCost getCost() {
            return cost;
        }

        public QueueType getQueueType() {
            return queueType;
        }
    }

    private List<ItemDataHolder> items;

    public ItemData(BufferedReader data) throws Exception {
        items = new ArrayList<ItemDataHolder>();
        String temp;

        // discard the header row
        data.readLine();

        while ((temp = data.readLine()) != null) {
            String[] values = temp.split(",");

            if (values.length != 6) {
                throw new RuntimeException("Invalid CSV row, expected 6 items, found " + values.length);
            }

            QueueType queueType;

            switch (values[1]) {
                case "0":
                    queueType = QueueType.LIGHT_ARMS;
                    break;
                case "1":
                    queueType = QueueType.HEAVY_ARMS;
                    break;
                case "2":
                    queueType = QueueType.HEAVY_SHELL;
                    break;
                case "3":
                    queueType = QueueType.UTILITIES;
                    break;
                case "4":
                    queueType = QueueType.MEDICAL;
                    break;
                case "5":
                    queueType = QueueType.UNIFORMS;
                    break;
                default:
                    throw new RuntimeException("Invalid queue type in database, row data: " + temp);
            }

            // cost are in multiple of 4, as queue are also multiple of 4.
            items.add(new ItemDataHolder(values[0], queueType, new ConstantCost(Integer.parseInt(values[2]) * 4,
                    Integer.parseInt(values[3]) * 4, Integer.parseInt(values[4]) * 4,
                    Integer.parseInt(values[5]) * 4)));
        }
    }

    public ItemData() throws Exception {
        // 8192 is the default buffer size, not modifying.
        this(Gdx.files.internal("itemData.csv").reader(8192));
    }

    public String getItemName(int id) {
        return items.get(id).getItemName();
    }

    /**
     * Return the cost of an item. Attempt at modifying the return value throw an Exception.
     * 
     * @param id The item's ID
     * @return Cost of the item
     */
    public ConstantCost getCost(int id) {
        return items.get(id).getCost();
    }

    public QueueType getQueueType(int id) {
        return items.get(id).getQueueType();
    }

    public int getItemDataSize() {
        return items.size();
    }
}
