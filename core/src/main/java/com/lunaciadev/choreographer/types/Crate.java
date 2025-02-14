package com.lunaciadev.choreographer.types;

public class Crate {
    private int id;
    private int queueNeeded;
    private int queueMade;
    private Priority priority;

    /**
     * Initialize a Crate with ID and amount required.
     * 
     * @param name        ItemID
     * @param priority    The priority of Crate
     * @param queueNeeded How many queue are needed
     */
    public Crate(int id, int queueNeeded, Priority priority) {
        this.id = id;
        this.queueNeeded = queueNeeded;
        this.queueMade = 0;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public int getQueueMade() {
        return queueMade;
    }

    public Priority getPriority() {
        return priority;
    }

    public int getQueueNeeded() {
        return queueNeeded;
    }

    /**
     * Call when a queue is made. Return true if it has reached the goal, false
     * otherwise
     */
    public boolean queueManufactured() {
        queueMade += 4;

        return queueMade >= queueNeeded;
    }

    public void undoQueueManufactured() {
        queueMade -= 4;
    }

    public boolean isCompleted() {
        return queueMade >= queueNeeded;
    }
}
