package com.lunaciadev.choreographer.types;

public class Crate {
    private int id;
    private int crateNeeded;
    private int crateMade;
    private int priority;

    /**
     * Initialize a Crate with ID and amount required.
     * 
     * @param name ItemID
     * @param crateNeeded How many crates are needed
     */
    public Crate(int id, int crateNeeded, int priority) {
        this.id = id;
        this.crateNeeded = crateNeeded;
        this.crateMade = 0;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public int getCrateMade() {
        return crateMade;
    }

    public int getPriority() {
        return priority;
    }

    public boolean queueManufactured() {
        crateMade += 4;
        
        return crateMade >= crateNeeded;
    }
}
