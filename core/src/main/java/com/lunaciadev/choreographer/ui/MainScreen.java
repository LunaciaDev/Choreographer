package com.lunaciadev.choreographer.ui;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.lunaciadev.choreographer.data.UIDataPackage;

public class MainScreen implements Screen {
    private Stage stage;
    private UIDataPackage uiDataPackage;
    private Table lightArmTable;
    private Table heavyArmTable;
    private Table heavyShellTable;
    private Table utilitiesTable;
    private Table medicalTable;
    private Table uniformTable;
    private Table resourceTable;

    public MainScreen(UIDataPackage uiDataPackage) {
        this.uiDataPackage = uiDataPackage;
        this.stage = new Stage(new ScreenViewport());
        layout();
    }

    private void layout() {
        Table rootTable = new Table();
        rootTable.setFillParent(true);

        Table toolbar = new Table();

        // TODO add component to Toolbar, when I have those... Subtitude with Labels for now.

        toolbar.add(new Label("Add", uiDataPackage.getSkin()));
        toolbar.add(new Label("Import from LogiHub", uiDataPackage.getSkin()));
        toolbar.add(new Label("Start Manu", uiDataPackage.getSkin()));

        rootTable.add(toolbar)
                .fill()
                .expandX();
        rootTable.row();

        Table content = new Table();

        content.defaults().expand().fill();

        lightArmTable = new Table();
        heavyArmTable = new Table();
        heavyShellTable = new Table();
        utilitiesTable = new Table();
        medicalTable = new Table();
        uniformTable = new Table();
        resourceTable = new Table();

        content.add(lightArmTable);
        content.add(heavyArmTable);
        content.add(heavyShellTable);
        content.add(utilitiesTable);
        content.add(medicalTable);
        content.add(uniformTable);
        content.add(resourceTable);

        rootTable.add(content)
                .fill()
                .expand();

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
