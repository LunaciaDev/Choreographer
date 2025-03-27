package com.lunaciadev.choreographer.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.lunaciadev.choreographer.core.Choreographer;
import com.lunaciadev.choreographer.data.ItemData;
import com.lunaciadev.choreographer.data.UIDataPackage;
import com.lunaciadev.choreographer.types.Truck;
import com.lunaciadev.choreographer.utils.Signal;


public class ItemList {
    private VerticalGroup table;
    private VerticalGroup group;
    
    private ItemData itemData;
    private Skin skin;

    public Signal removeItemAt = new Signal();

    public ItemList(UIDataPackage uiDataPackage) {
        this.table = new VerticalGroup();
        this.group = new VerticalGroup();
        this.itemData = uiDataPackage.getItemData();
        this.skin = uiDataPackage.getSkin();

        table.space(5);
        group.space(10);

        group.addActor(new Label("Queues: ", skin));
        group.addActor(table);
    }

    private void resetTable(Truck truck) {
        table.clearChildren();

        int count = 0;

        for (int id : truck.getTruckContent()) {
            final int buttonID = count;

            Table temp = new Table();
            Label label = new Label(itemData.getItemName(id), skin);
            TextButton remove = new TextButton("x", skin, "no-highlight");

            remove.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    removeItemAt.emit(buttonID);
                }
            });

            temp.defaults().space(5);

            temp.add(label);
            temp.add(remove)
                .height(remove.getLabel().getPrefHeight() + 10)
                .width(remove.getLabel().getPrefWidth() + 10);

            table.addActor(temp);

            count++;
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
        resetTable((Truck) args[0]);
    }

    public VerticalGroup getWidget() {
        return group;
    }
}
