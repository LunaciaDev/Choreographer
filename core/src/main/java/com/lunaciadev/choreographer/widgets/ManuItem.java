package com.lunaciadev.choreographer.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.lunaciadev.choreographer.data.ItemData;
import com.lunaciadev.choreographer.data.UIDataPackage;
import com.lunaciadev.choreographer.types.Cost;
import com.lunaciadev.choreographer.types.Crate;
import com.lunaciadev.choreographer.utils.Signal;

public class ManuItem extends WidgetGroup {
    private ItemData itemData;
    private Skin skin;
    private Label nameLabel;
    private Label amountLabel;
    private Label costLabel;
    private Label priorityLabel;
    private boolean isFirstItem;
    private Table rootTable;

    private Crate data;
    private Signal editSignal;
    private Signal deleteSignal;

    // How much space is padded around the widget (not margin, PADDING)
    private final float outerPadding = 10;
    // How much space is padded inbetween row of the widget
    private final float innerPadding = 5;

    public ManuItem(UIDataPackage uiDataPackage, Signal editSignal, Signal deleteSignal) {
        this.itemData = uiDataPackage.getItemData();
        this.skin = uiDataPackage.getSkin();
        this.editSignal = editSignal;
        this.deleteSignal = deleteSignal;
        setLayout();
    }

    public void setLayout() {
        rootTable = new Table();
        rootTable.pad(outerPadding);

        // TODO add a figure, rn we ignore

        Table firstRow = new Table();

        nameLabel = new Label("[PH]", skin);
        amountLabel = new Label("[PH]", skin);
        amountLabel.setAlignment(Align.right);

        firstRow.add(nameLabel)
            .prefWidth(Value.percentWidth(0.8f, firstRow))
            .minWidth(nameLabel.getWidth());
        firstRow.add(amountLabel)
            .prefWidth(Value.percentWidth(0.2f, firstRow))
            .minWidth(amountLabel.getWidth());

        rootTable.add(firstRow).expandX().fill().pad(0, 0, innerPadding, 0);
        rootTable.row();

        costLabel = new Label("[PH]", skin);

        rootTable.add(costLabel).pad(0, 0, innerPadding, 0);
        rootTable.row();

        priorityLabel = new Label("[PH]", skin);
        priorityLabel.setAlignment(Align.left);

        TextButton editButton = new TextButton("Edit", skin);

        editButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                editSignal.emit(data);
            }
        });

        TextButton deleteButton = new TextButton("Delete", skin);

        deleteButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                deleteSignal.emit(data);
                data = null;
            }
        });

        Table lastRow = new Table();

        lastRow.add(priorityLabel)
                .expandX()
                .fillX();
        lastRow.add(editButton)
                .pad(0, 0, 0, 5);
        lastRow.add(deleteButton);

        rootTable.add(lastRow)
                .expandX()
                .fill();
        
        this.addActor(rootTable);
        rootTable.setFillParent(true);
    }

    public void setData(Crate crate) {
        this.data = crate;
        nameLabel.setText(itemData.getItemName(crate.getId()));
        amountLabel.setText(Integer.toString(crate.getQueueNeeded()));
        costLabel.setText(generateCostString(itemData.getCost(crate.getId())));
        priorityLabel.setText(crate.getPriority().getReadableName());
        invalidateHierarchy();
    }

    private String generateCostString(Cost itemCost) {
        StringBuilder costString = new StringBuilder();
        isFirstItem = true;

        addToString(itemCost.getBmatCost(), costString, 0);
        addToString(itemCost.getEmatCost(), costString, 1);
        addToString(itemCost.getHematCost(), costString, 2);
        addToString(itemCost.getRmatCost(), costString, 3);

        return costString.toString();
    }

    private void addToString(int cost, StringBuilder costString, int type) {
        if (cost == 0) return;

        if (!isFirstItem) {
            costString.append(", ");
        }

        costString.append(Integer.toString(cost));

        switch(type) {
            case 0: costString.append("b"); break;
            case 1: costString.append("e"); break;
            case 2: costString.append("he"); break;
            case 3: costString.append("r"); break;
        }

        isFirstItem = false;
    }

    @Override
    public float getPrefHeight() {
        return nameLabel.getPrefHeight() * 3 + outerPadding * 2 + innerPadding * 2;
    }

    @Override
    public float getPrefWidth() {
        float maxWidth = 0;
        for (Actor child : getChildren()) {
            maxWidth = Math.max(maxWidth, child.getRight());
        }
        return maxWidth;  // Dynamically calculates width
    }
}