package com.lunaciadev.choreographer.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.lunaciadev.choreographer.core.InputHandler;
import com.lunaciadev.choreographer.data.ItemData;
import com.lunaciadev.choreographer.data.UIDataPackage;
import com.lunaciadev.choreographer.types.Crate;
import com.lunaciadev.choreographer.types.QueueType;
import com.lunaciadev.choreographer.utils.Signal;

public class ItemColumn {
    private Array<Crate> crateArray;
    private Pool<ManuItem> manuItemPool;
    private Table table;
    private QueueType columnType;
    private ItemData itemData;

    private final float cellPadding = 5;

    /**
     * Emitted when one of its ManuItem has its editButton clicked.
     * 
     * @param crate {@link Crate} The item's data.
     */
    public Signal editButtonClicked;

    /**
     * Emitted when one of its ManuItem has its deleteButton clicked.
     * 
     * @param crate {@link Crate} The item's data.
     */
    public Signal deleteButtonClicked;

    public ItemColumn(Table table, UIDataPackage uiDataPackage, QueueType columnType) {
        this.table = table;
        this.itemData = uiDataPackage.getItemData();
        this.columnType = columnType;

        table.setDebug(true);
        table.top();

        crateArray = new Array<>();
        manuItemPool = new Pool<ManuItem>() {
            @Override
            protected ManuItem newObject() {
                return new ManuItem(uiDataPackage, editButtonClicked, deleteButtonClicked);
            }
        };

        editButtonClicked = new Signal();
        deleteButtonClicked = new Signal();
    }

    /**
     * Slot, triggered by {@link InputHandler#crateAdded}
     */
    public void onAddItem(Object... args) {
        Crate crate = (Crate) args[0];

        if (itemData.getQueueType(crate.getId()) != columnType) return;

        ManuItem manuItem = manuItemPool.obtain();

        table.add(manuItem)
                .expandX()
                .fill()
                .pad(cellPadding);
        table.row();

        manuItem.setData(crate);

        crateArray.add(crate);
        dataModified();
    }

    /**
     * Slot, triggered by {@link InputHandler#crateAdded}
     */
    public void onDataModified(Object... args) {
        Crate crate = (Crate) args[0];

        if (itemData.getQueueType(crate.getId()) != columnType) return;
        dataModified();
    }

    public void dataModified() {
        crateArray.sort(Crate.compareByQueue);
        crateArray.sort(Crate.compareByPriority);

        Array<Actor> manuItems = table.getChildren();

        for (int i = 0; i < manuItems.size; i++) {
            ((ManuItem) manuItems.get(i)).setData(crateArray.get(i));
        }

        // item size are fixed, just call invalidate to be certain.
        table.invalidate();
    }

    /**
     * Slot, triggered by {@link InputHandler#crateDeleted}
     */
    public void onCrateDeleted(Object... args) {
        Crate crate = (Crate) args[0];

        if (itemData.getQueueType(crate.getId()) != columnType) return;

        // we should have the reference of that crate too. Would be VERY FUNNY if we do not.
        int index = crateArray.indexOf(crate, true);
        ManuItem manuItem = (ManuItem) table.getChild(index);
        
        crateArray.removeIndex(index);
        table.removeActorAt(index, false);
        manuItemPool.free(manuItem);

        dataModified();
    }

    public Array<Crate> getCrateArray() {
        return crateArray;
    }
}
