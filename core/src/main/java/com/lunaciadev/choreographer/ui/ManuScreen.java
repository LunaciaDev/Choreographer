package com.lunaciadev.choreographer.ui;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.lunaciadev.choreographer.core.Choreographer;
import com.lunaciadev.choreographer.core.ResultGenerator;
import com.lunaciadev.choreographer.data.UIDataPackage;
import com.lunaciadev.choreographer.types.Crate;
import com.lunaciadev.choreographer.types.EventType;
import com.lunaciadev.choreographer.types.QueueType;
import com.lunaciadev.choreographer.utils.GlobalKeyListener;
import com.lunaciadev.choreographer.utils.Signal;
import com.lunaciadev.choreographer.widgets.CostLabel;
import com.lunaciadev.choreographer.widgets.ItemList;
import com.lunaciadev.choreographer.widgets.ProgressDisplay;
import com.lunaciadev.choreographer.widgets.QueueTypeHighlight;

public class ManuScreen implements Screen {
    private UIDataPackage uiDataPackage;
    private Stage stage;
    private Choreographer choreographer;
    private GlobalKeyListener keyListener;

    public Signal returnButtonClicked = new Signal();

    public ManuScreen(UIDataPackage uiDataPackage) {
        this.uiDataPackage = uiDataPackage;
        this.stage = new Stage(new ScreenViewport());
        this.choreographer = new Choreographer(uiDataPackage.getItemData());
        this.keyListener = new GlobalKeyListener();

        choreographer.reachedManuGoal.connect(this::onStop);
    }

    private void onKeyEvent(Object... args) {
        EventType eventType = (EventType) args[0];

        switch (eventType) {
            case QUEUE_ORDER:
                choreographer.queueRequest((QueueType) args[1]);
                break;
            case TRUCK_SUBMIT:
                choreographer.submitTruck();
                break;
            case UNDO:
                choreographer.undoRequest();
                break;
            case QUIT:
                choreographer.getResult();
                break;
        }
    }

    @SuppressWarnings("unchecked")
    private void onStop(Object... args) {
        Table rootTable = (Table) stage.getActors().first();
        ResultGenerator generator = new ResultGenerator(uiDataPackage.getItemData());
        
        generator.getResult((HashMap<Integer, Crate>) args[0]);

        TextArea textArea = new TextArea(generator.generateGoogleSheetPaste(), uiDataPackage.getSkin());
        textArea.setTextFieldFilter(new TextFieldFilter() {
            public boolean acceptChar (TextField textField, char c) {
                return false;
            }
        });

        TextButton button = new TextButton("Exit", uiDataPackage.getSkin());

        rootTable.clearChildren();
        rootTable.add(new Label("Manufacture Finished!", uiDataPackage.getSkin()));
        rootTable.row();
        rootTable.add(textArea);
        rootTable.row();
        rootTable.add(button)
                .height(button.getLabel().getPrefHeight() + 10)
                .width(button.getLabel().getPrefWidth() + 10);
;
    }

    private void setLayout() {
        Table rootTable = new Table();
        rootTable.setFillParent(true);

        rootTable.defaults().pad(5, 0, 5, 0);

        /*
         * For the UI, I'm thinking about something like this:
         * 
         * First row show how many truck are queued + percentage toward the manu goal
         * Second row show the current stuff to be cooked
         * Third row show the resource cost.
         * Fourth row show which queues type still have item.
         */

        // Setting up the first row - header

        ProgressDisplay progressDisplay = new ProgressDisplay(uiDataPackage);

        choreographer.update.connect(progressDisplay::onUpdate);

        rootTable.add(progressDisplay.getWidget());
        rootTable.row();

        // Setting up the second row - current item that need to be cooked

        ItemList itemList = new ItemList(uiDataPackage);

        choreographer.queueRequestComplete.connect(itemList::onQueueRequestComplete);
        choreographer.truckSubmitted.connect(itemList::onTruckSubmitted);
        choreographer.undoRequestComplete.connect(itemList::onUndoRequestComplete);
        
        rootTable.add(itemList.getWidget());
        rootTable.row();

        // Setting up the third row - material need to be pulled

        CostLabel costLabel = new CostLabel(uiDataPackage);

        choreographer.queueRequestComplete.connect(costLabel::onItemQueued);
        choreographer.truckSubmitted.connect(costLabel::onTruckSubmitted);

        rootTable.add(costLabel.getWidget());
        rootTable.row();

        // Setting up the fourth row - queue type that still have item

        QueueTypeHighlight queueTypeHighlight = new QueueTypeHighlight(uiDataPackage);
        
        choreographer.queueCompleted.connect(queueTypeHighlight::setBackground);

        for (QueueType queueType : QueueType.values()) {
            choreographer.isQueueCompleted(queueType);
        }

        rootTable.add(queueTypeHighlight.getWidget());
        // etc...

        stage.addActor(rootTable);
    }

    @Override
    public void show() {
        keyListener.keyEvent.connect(this::onKeyEvent);
        keyListener.activateListener();
        choreographer.setData(uiDataPackage.getInputHandler());

        Gdx.input.setInputProcessor(stage);
        Gdx.graphics.setWindowedMode(280, 250);
        setLayout();
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
        keyListener.deactivateListener();
    }

    @Override
    public void resume() {
        keyListener.activateListener();
    }

    @Override
    public void hide() {
        keyListener.deactivateListener();
        ((Table) stage.getActors().first()).clear();
    }

    @Override
    public void dispose() {
        keyListener.deactivateListener();
    }

}