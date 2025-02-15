package com.lunaciadev.choreographer.core;

import java.util.HashMap;

import com.lunaciadev.choreographer.types.Crate;
import com.lunaciadev.choreographer.types.Priority;
import com.lunaciadev.choreographer.ui.AddItemPopup;
import com.lunaciadev.choreographer.utils.Signal;

public class InputHandler {
    private HashMap<Integer, Crate> inputCrates;

    /**
     * Emitted when a crate is added to the handler.
     * 
     * @param crate {@link Crate} The added crate.
     */
    public Signal crateAdded;

    public InputHandler() {
        inputCrates = new HashMap<Integer, Crate>();
        crateAdded = new Signal();
    }

    /**
     * In theory these methods need not be unit tested since they are only simple
     * wrapper of HashMap, so.
     * 
     * LogiHub import will communicate with the InputHandler as well.
     */

    /**
     * Slot, triggered by {@link AddItemPopup#addItemFormSubmitted}
     */
    public void addCrate(Object... args) {
        int id = (int) args[0];
        Priority priority = (Priority) args[1];
        int manufactureGoal = (int) args[2];

        inputCrates.put(id, new Crate(id, manufactureGoal, priority));

        crateAdded.emit(inputCrates.get(id));
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
