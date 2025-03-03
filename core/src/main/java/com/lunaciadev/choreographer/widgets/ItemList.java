package com.lunaciadev.choreographer.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.lunaciadev.choreographer.core.Choreographer;
import com.lunaciadev.choreographer.data.ItemData;
import com.lunaciadev.choreographer.data.UIDataPackage;
import com.lunaciadev.choreographer.types.Truck;


public class ItemList {
    private VerticalGroup table;
    
    private ItemData itemData;
    private Skin skin;

    public ItemList(UIDataPackage uiDataPackage) {
        this.table = new VerticalGroup();
        this.itemData = uiDataPackage.getItemData();
        this.skin = uiDataPackage.getSkin();
    }

    private void resetTable(Truck truck) {
        table.clearChildren();

        for (int id : truck.getTruckContent()) {
            Label label = new Label(itemData.getItemName(id), skin);
            table.addActor(label);
        }
    }

    /**
     * Slot, triggered by {@link Choreographer#queueRequestComplete}
     */
    public void onQueueRequestComplete(Object... args) {
        if (!(boolean) args[0]) return;

        resetTable((Truck) args[1]);
    }

    /**
     * Slot, triggered by {@link Choreographer#truckSubmitted}
     */
    public void onTruckSubmitted(Object... args) {
        resetTable((Truck) args[2]);
    }

    /**
     * Slot, triggered by {@link Choreographer#undoRequestComplete}
     */
    public void onUndoRequestComplete(Object... args) {
        if (!(boolean) args[0]) return;

        table.removeActorAt(table.getChildren().size - 1, false);
    }

    public VerticalGroup getWidget() {
        return table;
    }
}
