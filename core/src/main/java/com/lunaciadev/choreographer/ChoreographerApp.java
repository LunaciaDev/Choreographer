package com.lunaciadev.choreographer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lunaciadev.choreographer.core.InputHandler;
import com.lunaciadev.choreographer.data.ItemData;
import com.lunaciadev.choreographer.data.UIDataPackage;
import com.lunaciadev.choreographer.ui.MainScreen;
import com.lunaciadev.choreographer.ui.ManuScreen;

public class ChoreographerApp extends Game {
    private MainScreen mainScreen;
    private ManuScreen manuScreen;
    private UIDataPackage uiDataPackage;

    @Override
    public void create() {
        Skin skin = new Skin(Gdx.files.internal("ui/skin.json"));
        ItemData itemData;

        try {
            itemData = new ItemData();
        } catch (Exception e) {
            // TODO: handle exception
            return;
        }

        uiDataPackage = new UIDataPackage(itemData, skin, new InputHandler(itemData));

        mainScreen = new MainScreen(uiDataPackage);
        manuScreen = new ManuScreen(uiDataPackage);

        mainScreen.startButtonClicked.connect(this::toManuScreen);
        manuScreen.returnButtonClicked.connect(this::toMainScreen);

        setScreen(mainScreen);
    }

    private void toManuScreen(Object... args) {
        setScreen(manuScreen);
    }

    private void toMainScreen(Object... args) {
        setScreen(mainScreen);
    }

    @Override
    public void dispose() {

    }
}
