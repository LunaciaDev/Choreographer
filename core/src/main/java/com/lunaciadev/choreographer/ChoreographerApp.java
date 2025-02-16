package com.lunaciadev.choreographer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lunaciadev.choreographer.data.ItemData;
import com.lunaciadev.choreographer.data.UIDataPackage;
import com.lunaciadev.choreographer.ui.MainScreen;

public class ChoreographerApp extends Game {
    private Screen currentScreen;
    private UIDataPackage uiDataPackage;

    @Override
    public void create() {
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        ItemData itemData;

        try {
            itemData = new ItemData();
        } catch (Exception e) {
            // TODO: handle exception
            return;
        }

        uiDataPackage = new UIDataPackage(itemData, skin);

        currentScreen = new MainScreen(uiDataPackage);
        this.screen = currentScreen;
    }

    @Override
    public void dispose() {

    }
}
