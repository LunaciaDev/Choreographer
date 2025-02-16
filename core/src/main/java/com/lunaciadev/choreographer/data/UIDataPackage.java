package com.lunaciadev.choreographer.data;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UIDataPackage {
    private ItemData itemData;
    private Skin skin;

    public UIDataPackage(ItemData itemData, Skin skin) {
        this.itemData = itemData;
        this.skin = skin;
    }

    public ItemData getItemData() {
        return itemData;
    }

    public Skin getSkin() {
        return skin;
    }
}
