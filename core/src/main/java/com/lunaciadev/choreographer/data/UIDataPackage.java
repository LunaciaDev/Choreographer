package com.lunaciadev.choreographer.data;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lunaciadev.choreographer.core.InputHandler;

public class UIDataPackage {
    private ItemData itemData;
    private Skin skin;
    private InputHandler inputHandler;

    public UIDataPackage(ItemData itemData, Skin skin, InputHandler inputHandler) {
        this.itemData = itemData;
        this.skin = skin;
        this.inputHandler = inputHandler;
    }

    public ItemData getItemData() {
        return itemData;
    }

    public Skin getSkin() {
        return skin;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }
}
