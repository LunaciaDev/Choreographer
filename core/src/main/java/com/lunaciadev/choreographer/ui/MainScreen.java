package com.lunaciadev.choreographer.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.lunaciadev.choreographer.core.InputHandler;
import com.lunaciadev.choreographer.data.UIDataPackage;
import com.lunaciadev.choreographer.types.QueueType;
import com.lunaciadev.choreographer.utils.Signal;
import com.lunaciadev.choreographer.widgets.ItemColumn;

public class MainScreen implements Screen {
    private Stage stage;
    private UIDataPackage uiDataPackage;
    private ItemColumn lightArmColumn;
    private ItemColumn heavyArmColumn;
    private ItemColumn heavyShellColumn;
    private ItemColumn utilitiesColumn;
    private ItemColumn medicalColumn;
    private ItemColumn uniformColumn;

    private AddItemPopup addItemPopup;
    private EditItemPopup editItemPopup;
    private InputHandler inputHandler;

    private TitleBarStyle titleStyle;

    /**
     * Emitted when the "add" button is clicked.
     * 
     * @param Stage the stage to show the popup
     */
    public Signal addButtonClicked = new Signal();

    /**
     * Emitted when the "manu" button is clicked.
     */
    public Signal startButtonClicked = new Signal();

    public MainScreen(UIDataPackage uiDataPackage) {
        this.uiDataPackage = uiDataPackage;
        this.stage = new Stage(new ScreenViewport());
        this.titleStyle = uiDataPackage.getSkin().get(TitleBarStyle.class);

        setLayout();
    }

    private void setLayout() {
        Table rootTable = new Table();
        rootTable.setFillParent(true);

        Table toolbar = new Table();
        toolbar.setBackground(titleStyle.background);

        // TODO add component to Toolbar, when I have those... Subtitude with Labels for now.

        TextButton addButton = new TextButton("Add", uiDataPackage.getSkin(), "no-background");
        addButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                addButtonClicked.emit(stage);
            }
        });

        TextButton startButton = new TextButton("Start Manu", uiDataPackage.getSkin(), "no-background");
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startButtonClicked.emit();
            }
        });

        toolbar.defaults().pad(5);

        toolbar.add(new Label("Choreographer", uiDataPackage.getSkin()));
        toolbar.add(addButton)
                .height(addButton.getLabel().getPrefHeight() + 10)
                .width(addButton.getLabel().getPrefWidth() + 10);
        toolbar.add(startButton)
                .height(startButton.getLabel().getPrefHeight() + 10)
                .width(startButton.getLabel().getPrefWidth() + 10);

        toolbar.left().pad(10, 15, 10, 15);

        rootTable.add(toolbar)
                .width(Value.percentWidth(1f, rootTable))
                .fill()
                .pad(5, 0, 5, 0);
        rootTable.row();

        Table content = new Table();

        content.defaults().pad(10, 0, 10, 0);

        content.add(new Label("Light Arms", uiDataPackage.getSkin())).center();
        content.add(new Label("Heavy Arms", uiDataPackage.getSkin())).center();
        content.add(new Label("Heavy Shells", uiDataPackage.getSkin())).center();
        content.add(new Label("Utilities", uiDataPackage.getSkin())).center();
        content.add(new Label("Medical", uiDataPackage.getSkin())).center();
        content.add(new Label("Uniforms", uiDataPackage.getSkin())).center();
        content.row();

        content.defaults().expandY().fill().width(Value.percentWidth(1/6f, rootTable));

        addItemPopup = new AddItemPopup(uiDataPackage, stage);
        editItemPopup = new EditItemPopup(uiDataPackage, stage);
        addButtonClicked.connect(addItemPopup::onAddNewItemButtonClicked);

        inputHandler = uiDataPackage.getInputHandler();
        addItemPopup.addItemFormSubmitted.connect(inputHandler::addCrate);
        editItemPopup.editItemFormSubmitted.connect(inputHandler::editCrate);

        lightArmColumn = new ItemColumn(uiDataPackage, QueueType.LIGHT_ARMS);
        inputHandler.crateAdded.connect(lightArmColumn::onAddItem);
        inputHandler.crateEdited.connect(lightArmColumn::onDataModified);
        inputHandler.crateDeleted.connect(lightArmColumn::onCrateDeleted);
        lightArmColumn.editButtonClicked.connect(editItemPopup::onEditItemButtonClicked);
        lightArmColumn.deleteButtonClicked.connect(inputHandler::removeCrate);

        heavyArmColumn = new ItemColumn(uiDataPackage, QueueType.HEAVY_ARMS);
        inputHandler.crateAdded.connect(heavyArmColumn::onAddItem);
        inputHandler.crateEdited.connect(heavyArmColumn::onDataModified);
        inputHandler.crateDeleted.connect(heavyArmColumn::onCrateDeleted);
        heavyArmColumn.editButtonClicked.connect(editItemPopup::onEditItemButtonClicked);
        heavyArmColumn.deleteButtonClicked.connect(inputHandler::removeCrate);

        heavyShellColumn = new ItemColumn(uiDataPackage, QueueType.HEAVY_AMMO);
        inputHandler.crateAdded.connect(heavyShellColumn::onAddItem);
        inputHandler.crateEdited.connect(heavyShellColumn::onDataModified);
        inputHandler.crateDeleted.connect(heavyShellColumn::onCrateDeleted);
        heavyShellColumn.editButtonClicked.connect(editItemPopup::onEditItemButtonClicked);
        heavyShellColumn.deleteButtonClicked.connect(inputHandler::removeCrate);

        utilitiesColumn = new ItemColumn(uiDataPackage, QueueType.UTILITIES);
        inputHandler.crateAdded.connect(utilitiesColumn::onAddItem);
        inputHandler.crateEdited.connect(utilitiesColumn::onDataModified);
        inputHandler.crateDeleted.connect(utilitiesColumn::onCrateDeleted);
        utilitiesColumn.editButtonClicked.connect(editItemPopup::onEditItemButtonClicked);
        utilitiesColumn.deleteButtonClicked.connect(inputHandler::removeCrate);

        medicalColumn = new ItemColumn(uiDataPackage, QueueType.MEDICAL);
        inputHandler.crateAdded.connect(medicalColumn::onAddItem);
        inputHandler.crateEdited.connect(medicalColumn::onDataModified);
        inputHandler.crateDeleted.connect(medicalColumn::onCrateDeleted);
        medicalColumn.editButtonClicked.connect(editItemPopup::onEditItemButtonClicked);
        medicalColumn.deleteButtonClicked.connect(inputHandler::removeCrate);

        uniformColumn = new ItemColumn(uiDataPackage, QueueType.UNIFORMS);
        inputHandler.crateAdded.connect(uniformColumn::onAddItem);
        inputHandler.crateEdited.connect(uniformColumn::onDataModified);
        inputHandler.crateDeleted.connect(uniformColumn::onCrateDeleted);
        uniformColumn.editButtonClicked.connect(editItemPopup::onEditItemButtonClicked);
        uniformColumn.deleteButtonClicked.connect(inputHandler::removeCrate);

        content.add(lightArmColumn.getColumn());
        content.add(heavyArmColumn.getColumn());
        content.add(heavyShellColumn.getColumn());
        content.add(utilitiesColumn.getColumn());
        content.add(medicalColumn.getColumn());
        content.add(uniformColumn.getColumn());

        rootTable.add(content).pad(0, 10, 0, 10).grow();

        stage.addActor(rootTable);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        uiDataPackage.getInputHandler().clearData();
        Gdx.graphics.setWindowedMode(1600, 900);

        lightArmColumn.clearColumn();
        heavyArmColumn.clearColumn();
        heavyShellColumn.clearColumn();
        utilitiesColumn.clearColumn();
        medicalColumn.clearColumn();
        uniformColumn.clearColumn();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(new Color(0x212529ff));
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

    public static class TitleBarStyle {
        public Drawable background;

        public TitleBarStyle() {}
    }
}
