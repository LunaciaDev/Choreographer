package com.lunaciadev.choreographer.core;

import java.util.HashMap;

import com.lunaciadev.choreographer.types.Crate;
import com.lunaciadev.choreographer.types.Priority;
import com.lunaciadev.choreographer.ui.AddItemPopup;
import com.lunaciadev.choreographer.ui.EditItemPopup;
import com.lunaciadev.choreographer.utils.Signal;
import com.lunaciadev.choreographer.widgets.ItemColumn;


public class InputHandler {
    private HashMap<Integer, Crate> inputCrates;

    /**
     * Emitted when a crate is added to the handler.
     * 
     * @param crate {@link Crate} The added crate.
     */
    public Signal crateAdded;

    /**
     * Emitted when a crate is edited.
     * 
     * @param crate {@link Crate} The edited crate. This crate is the exact same
     *              object as the old crate (equality operator yield true).
     */
    public Signal crateEdited;

    /**
     * Emitted when a crate is deleted.
     * 
     * @param crate {@link Crate} The deleted crate.
     */
    public Signal crateDeleted;

    public InputHandler() {
        inputCrates = new HashMap<Integer, Crate>();
        crateAdded = new Signal();
        crateEdited = new Signal();
        crateDeleted = new Signal();
    }

    private final int[] modulusLookup = {4, 1, 2, 3};

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

        manufactureGoal += 4 - modulusLookup[manufactureGoal % 4];

        /**
         * If crate already exists, switch to editCrate method as this method if used
         * when the crate already exist cause inconsistent state between data and UI.
         */
        if (inputCrates.containsKey(id)) {
            editCrate(args);
            return;
        }

        inputCrates.put(id, new Crate(id, manufactureGoal, priority));

        crateAdded.emit(inputCrates.get(id));
    }

    /**
     * Slot, triggered by {@link EditItemPopup#editItemFormSubmitted}
     */
    public void editCrate(Object... args) {
        int id = (int) args[0];
        Priority priority = (Priority) args[1];
        int manufactureGoal = (int) args[2];

        Crate targetCrate = inputCrates.get(id);

        targetCrate.setPriority(priority);
        targetCrate.setQueueNeeded(manufactureGoal);

        crateEdited.emit(targetCrate);
    }

    /**
     * Slot, triggered by {@link ItemColumn#deleteButtonClicked}
     */
    public void removeCrate(Object... args) {
        int id = ((Crate) args[0]).getId();

        /**
         * There must be a crate here for it to show up in the UI.
         */
        inputCrates.remove(id);
        crateDeleted.emit(args[0]);
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
