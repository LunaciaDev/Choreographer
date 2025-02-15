package com.lunaciadev.choreographer.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.lunaciadev.choreographer.core.InputHandler;
import com.lunaciadev.choreographer.data.UIDataPackage;
import com.lunaciadev.choreographer.types.Crate;
import com.lunaciadev.choreographer.types.QueueType;

public class ItemColumn {
    private Array<Crate> crateArray;
    private Pool<ManuItem> manuItemPool;
    private Table table;
    private UIDataPackage uiDataPackage;
    private QueueType columnType;

    public ItemColumn(Table table, UIDataPackage uiDataPackage, QueueType columnType) {
        this.table = table;
        this.uiDataPackage = uiDataPackage;
        this.columnType = columnType;

        crateArray = new Array<>();
        manuItemPool = new Pool<ManuItem>() {
            @Override
            protected ManuItem newObject() {
                return new ManuItem(uiDataPackage);
            }
        };
    }

    /**
     * Slot, triggered by {@link InputHandler#crateAdded}
     */
    public void onAddItem(Object... args) {
        Crate crate = (Crate) args[0];

        if (uiDataPackage.getItemData().getQueueType(crate.getId()) != columnType) return;

        ManuItem manuItem = manuItemPool.obtain();
        table.add(manuItem);
        table.row();

        manuItem.setData(crate);

        crateArray.add(crate);
        dataModified();
    }

    public void onDataModified(Crate crate) {
        if (uiDataPackage.getItemData().getQueueType(crate.getId()) != columnType) return;
        dataModified();
    }

    // need to sort crateArray again.
    public void dataModified() {
        crateArray.sort(Crate.compareByQueue);
        crateArray.sort(Crate.compareByPriority);

        Array<Actor> cells = table.getChildren();

        for (int i = 0; i < cells.size; i++) {
            ((ManuItem) cells.get(i)).setData(crateArray.get(i));
        }

        // item size are fixed, just call invalidate to be certain.
        table.invalidate();
    }

    public void removeItem(Crate crate) {
        if (uiDataPackage.getItemData().getQueueType(crate.getId()) != columnType) return;

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
