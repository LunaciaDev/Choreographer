package com.lunaciadev.choreographer.data;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.lunaciadev.choreographer.types.Cost;

public class ItemData {
    enum QueueType {
        SMALL_ARMS,
        HEAVY_ARMS,
        HEAVY_AMMO,
        UTILITIES,
        MEDICAL,
        UNIFORMS
    }

    private class ItemDataHolder {
        private QueueType queueType;
        private String itemName;
        private Cost cost;

        public ItemDataHolder(String itemName, QueueType queueType, Cost cost) {
            this.itemName = itemName;
            this.queueType = queueType;
            this.cost = cost;
        }

        public String getItemName() {
            return itemName;
        }

        public Cost getCost() {
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

        while ((temp = data.readLine()) != null) {
            String[] values = temp.split(",");

            if (values.length != 6) {
                throw new RuntimeException("Invalid CSV row, expected 6 items, found " + values.length);
            }

            QueueType queueType;

            switch (values[1]) {
                case "0":
                    queueType = QueueType.SMALL_ARMS;
                    break;
                case "1":
                    queueType = QueueType.HEAVY_ARMS;
                    break;
                case "2":
                    queueType = QueueType.HEAVY_AMMO;
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

            items.add(new ItemDataHolder(values[0], queueType, new Cost(Integer.parseInt(values[2]),
                    Integer.parseInt(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5]))));
        }
    }

    public ItemData() throws Exception {
        // 8192 is the default buffer size, not modifying.
        this(Gdx.files.internal("itemData.csv").reader(8192));
    }

    public String getItemName(int id) {
        return items.get(id).getItemName();
    }

    public Cost getCost(int id) {
        return items.get(id).getCost();
    }

    public QueueType getQueueType(int id) {
        return items.get(id).getQueueType();
    }
}
