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
    private ItemColumn resourceColumn;

    private AddItemPopup addItemPopup;
    private EditItemPopup editItemPopup;
    private InputHandler inputHandler;

    /**
     * Emitted when the "add" button is clicked.
     * 
     * @param Stage the stage to show the popup
     */
    public Signal addButtonClicked = new Signal();

    public MainScreen(UIDataPackage uiDataPackage) {
        this.uiDataPackage = uiDataPackage;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        setLayout();
    }

    private void setLayout() {
        Table rootTable = new Table();
        rootTable.setFillParent(true);

        Table toolbar = new Table();

        // TODO add component to Toolbar, when I have those... Subtitude with Labels for now.

        TextButton addButton = new TextButton("Add", uiDataPackage.getSkin());
        addButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                addButtonClicked.emit(stage);
            }
        });

        toolbar.add(addButton);

        toolbar.add(new Label("Add", uiDataPackage.getSkin()));
        toolbar.add(new Label("Import from LogiHub", uiDataPackage.getSkin()));
        toolbar.add(new Label("Start Manu", uiDataPackage.getSkin()));

        rootTable.add(toolbar)
                .fill()
                .expandX();
        rootTable.row();

        Table content = new Table();

        content.defaults().expandY().fill().width(Value.percentWidth(1/7f, rootTable));

        Table lightArmTable = new Table();
        Table heavyArmTable = new Table();
        Table heavyShellTable = new Table();
        Table utilitiesTable = new Table();
        Table medicalTable = new Table();
        Table uniformTable = new Table();
        Table resourceTable = new Table();

        content.add(lightArmTable);
        content.add(heavyArmTable);
        content.add(heavyShellTable);
        content.add(utilitiesTable);
        content.add(medicalTable);
        content.add(uniformTable);
        content.add(resourceTable);

        rootTable.add(content).grow();

        stage.addActor(rootTable);

        addItemPopup = new AddItemPopup(uiDataPackage, stage);
        editItemPopup = new EditItemPopup(uiDataPackage, stage);
        addButtonClicked.connect(addItemPopup::onAddNewItemButtonClicked);

        inputHandler = new InputHandler();
        addItemPopup.addItemFormSubmitted.connect(inputHandler::addCrate);
        editItemPopup.editItemFormSubmitted.connect(inputHandler::editCrate);

        lightArmColumn = new ItemColumn(lightArmTable, uiDataPackage, QueueType.LIGHT_ARMS);
        inputHandler.crateAdded.connect(lightArmColumn::onAddItem);
        inputHandler.crateEdited.connect(lightArmColumn::onDataModified);
        lightArmColumn.editButtonClicked.connect(editItemPopup::onEditItemButtonClicked);

        heavyArmColumn = new ItemColumn(heavyArmTable, uiDataPackage, QueueType.HEAVY_ARMS);
        inputHandler.crateAdded.connect(heavyArmColumn::onAddItem);
        inputHandler.crateEdited.connect(heavyArmColumn::onDataModified);
        heavyArmColumn.editButtonClicked.connect(editItemPopup::onEditItemButtonClicked);

        heavyShellColumn = new ItemColumn(heavyShellTable, uiDataPackage, QueueType.HEAVY_AMMO);
        inputHandler.crateAdded.connect(heavyShellColumn::onAddItem);
        inputHandler.crateEdited.connect(heavyShellColumn::onDataModified);
        heavyShellColumn.editButtonClicked.connect(editItemPopup::onEditItemButtonClicked);

        utilitiesColumn = new ItemColumn(utilitiesTable, uiDataPackage, QueueType.UTILITIES);
        inputHandler.crateAdded.connect(utilitiesColumn::onAddItem);
        inputHandler.crateEdited.connect(utilitiesColumn::onDataModified);
        utilitiesColumn.editButtonClicked.connect(editItemPopup::onEditItemButtonClicked);

        medicalColumn = new ItemColumn(medicalTable, uiDataPackage, QueueType.MEDICAL);
        inputHandler.crateAdded.connect(medicalColumn::onAddItem);
        inputHandler.crateEdited.connect(medicalColumn::onDataModified);
        medicalColumn.editButtonClicked.connect(editItemPopup::onEditItemButtonClicked);

        uniformColumn = new ItemColumn(uniformTable, uiDataPackage, QueueType.UNIFORMS);
        inputHandler.crateAdded.connect(uniformColumn::onAddItem);
        inputHandler.crateEdited.connect(uniformColumn::onDataModified);
        uniformColumn.editButtonClicked.connect(editItemPopup::onEditItemButtonClicked);

        resourceColumn = new ItemColumn(resourceTable, uiDataPackage, QueueType.MATERIALS);
        inputHandler.crateAdded.connect(resourceColumn::onAddItem);
        inputHandler.crateEdited.connect(resourceColumn::onDataModified);
        resourceColumn.editButtonClicked.connect(editItemPopup::onEditItemButtonClicked);
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
