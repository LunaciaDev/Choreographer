package com.lunaciadev.choreographer.types;

public class Crate {
    enum QueueType {
        SMALL_ARMS,
        HEAVY_ARMS,
        HEAVY_AMMO,
        UTILITIES,
        MEDICAL,
        UNIFORMS
    }

    private int id;
    private QueueType type;

    private int amount;

    /**
     * Initialize a Crate, with name, queue type and amount required.
     * 
     * @param name ItemID
     * @param type Queue Type of the item
     * @param amount How many crates are needed
     */
    public Crate(int id, QueueType type, int amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public QueueType getType() {
        return type;
    }

    public boolean dequeue() {
        amount -= 4;
        
        return amount <= 0;
    }
}
