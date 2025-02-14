package com.lunaciadev.choreographer.core;

import java.util.HashMap;

import com.lunaciadev.choreographer.types.Crate;
import com.lunaciadev.choreographer.types.Priority;

public class InputHandler {
    private HashMap<Integer, Crate> inputCrates;

    public InputHandler() {
        inputCrates = new HashMap<Integer, Crate>();
    }

    /**
     * In theory these methods need not be unit tested since they are only simple
     * wrapper of HashMap, so.
     * 
     * LogiHub import will communicate with the InputHandler as well.
     */

    /**
     * Add a crate into the handler. If the crate has been added previously,
     * overwrite their value.
     * 
     * @param id              Item ID
     * @param priority        Manufacturing Priority (0-2 from High to Low)
     * @param manufactureGoal How many queue to set as the manu goal
     */
    public void addCrate(int id, Priority priority, int manufactureGoal) {
        inputCrates.put(id, new Crate(id, manufactureGoal, priority));
    }

    /**
     * Remove a crate from the handler. If the crate does not exists, do nothing.
     * 
     * @param id Item ID
     */
    public void removeCrate(int id) {
        if (inputCrates.containsKey(id)) {
            inputCrates.remove(id);
        }
    }

    public void clearData() {
        inputCrates.clear();
    }

    /**
     * Get all the crate that has been recorded as a HashMap.
     * 
     * @return HashMap of recorded Crates.
     */
    public HashMap<Integer, Crate> getData() {
        return inputCrates;
    }
}
