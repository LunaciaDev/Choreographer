package com.lunaciadev.choreographer.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.lunaciadev.choreographer.data.UIDataPackage;

public class ManuScreen implements Screen {
    private UIDataPackage uiDataPackage;
    private Stage stage;

    private Skin skin;

    public ManuScreen(UIDataPackage uiDataPackage) {
        this.uiDataPackage = uiDataPackage;
        this.stage = new Stage(new ScreenViewport());
        this.skin = uiDataPackage.getSkin();
        Gdx.input.setInputProcessor(stage);
        setLayout();
    }

    private void setLayout() {
        Table rootTable = new Table();
        rootTable.setFillParent(true);

        /*
         * For the UI, I'm thinking about something like this:
         * 
         * First row show how many truck are queued + percentage toward the manu goal
         * Second row show the current stuff to be cooked
         * Third row show the resource cost.
         * Fourth row show which queues type still have item.
         */

        // Setting up the first row - header

        // TODO: setup a custom widget for this..... man.
        // Looks something like this

        HorizontalGroup header = new HorizontalGroup();
        header.grow();

        header.addActor(new Label("2 orders queued", skin));
        header.addActor(new Label("43% of Manufacture Goal completed", skin));

        rootTable.add(header);
        rootTable.row();

        // Setting up the second row - current item that need to be cooked

        // TODO: setup a custom widget for this.

        // Looks something like this

        VerticalGroup currentItems = new VerticalGroup();
        currentItems.grow();

        currentItems.addActor(new Label("Booker Rifle", skin));
        currentItems.addActor(new Label("Officer Regalia", skin));

        rootTable.add(currentItems);
        rootTable.row();

        // Setting up the third row - material need to be pulled

        // TODO: setup a custom widget for this also

        // Look something like this

        rootTable.add(new Label("600b, 320e, 120r, 10he", skin));
        rootTable.row();

        // Setting up the fourth row - queue type that still have item

        // TODO: setup a custom widget for this also also

        // Look something like this

        HorizontalGroup typesHaveItems = new HorizontalGroup();
        typesHaveItems.grow();

        typesHaveItems.addActor(new Label("Light Arms", skin)); // green BG for active, hidden/red BG for inactive?
        typesHaveItems.addActor(new Label("Heavy Arms", skin));
        // etc...

        // why are everything stubs.

        stage.addActor(rootTable);
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show'");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.GRAY);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pause'");
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resume'");
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hide'");
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dispose'");
    }

}