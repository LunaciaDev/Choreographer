package com.lunaciadev.choreographer.types;

public class Crate {
    private int id;
    private int crateNeeded;
    private int crateMade;

    /**
     * Initialize a Crate with ID and amount required.
     * 
     * @param name ItemID
     * @param crateNeeded How many crates are needed
     */
    public Crate(int id, int crateNeeded) {
        this.id = id;
        this.crateNeeded = crateNeeded;
        this.crateMade = 0;
    }

    public int getId() {
        return id;
    }

    public int getCrateMade() {
        return crateMade;
    }

    public boolean queueManufactured() {
        crateMade += 4;
        
        return crateMade >= crateNeeded;
    }
}
