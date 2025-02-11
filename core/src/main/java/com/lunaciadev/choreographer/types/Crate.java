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

    private String name;
    private QueueType type;

    private int amount;

    public Crate(String name, QueueType type, int amount) {
        this.name = name;
        this.type = type;
        this.amount = amount;
    }
    public String getName() {
        return name;
    }

    public QueueType getType() {
        return type;
    }

    public boolean dequeue() {
        amount -= 4;
        
        return amount == 0;
    }
}
