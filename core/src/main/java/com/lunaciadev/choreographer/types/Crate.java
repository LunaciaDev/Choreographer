package com.lunaciadev.choreographer.types;

public class Crate {
    private int id;
    private int amount;

    /**
     * Initialize a Crate with ID and amount required.
     * 
     * @param name ItemID
     * @param amount How many crates are needed
     */
    public Crate(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public boolean dequeue() {
        amount -= 4;
        
        return amount <= 0;
    }
}
