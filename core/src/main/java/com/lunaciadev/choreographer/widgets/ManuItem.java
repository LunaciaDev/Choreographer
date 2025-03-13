package com.lunaciadev.choreographer.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.lunaciadev.choreographer.data.ItemData;
import com.lunaciadev.choreographer.data.UIDataPackage;
import com.lunaciadev.choreographer.types.Crate;
import com.lunaciadev.choreographer.utils.CostStringGenerator;
import com.lunaciadev.choreographer.utils.Signal;

public class ManuItem extends WidgetGroup {
    private ManuItemStyle style;

    private ItemData itemData;
    private Skin skin;
    private Label nameLabel;
    private Label amountLabel;
    private Label costLabel;
    private Label priorityLabel;
    private Table rootTable;

    private CostStringGenerator costStringGenerator;

    private Crate data;
    private Signal editSignal;
    private Signal deleteSignal;

    // How much space is padded around the widget (not margin, PADDING)
    private final float outerPadding = 15;
    // How much space is padded inbetween row of the widget
    private final float innerPadding = 15;

    public ManuItem() {}

    public ManuItem(UIDataPackage uiDataPackage, Signal editSignal, Signal deleteSignal) {
        this.itemData = uiDataPackage.getItemData();
        this.skin = uiDataPackage.getSkin();
        this.editSignal = editSignal;
        this.deleteSignal = deleteSignal;
        this.costStringGenerator = new CostStringGenerator();

        this.style = skin.get(ManuItemStyle.class);
        setLayout();
    }

    public void setLayout() {
        rootTable = new Table();
        rootTable.pad(outerPadding);

        rootTable.setBackground(style.background);

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

        TextButton editButton = new TextButton("Edit", skin, "no-highlight");

        editButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                editSignal.emit(data);
            }
        });

        TextButton deleteButton = new TextButton("Delete", skin, "no-highlight");

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
                .pad(0, 0, 0, 10)
                .height(editButton.getLabel().getPrefHeight() + 10)
                .width(editButton.getLabel().getPrefWidth() + 10);
        lastRow.add(deleteButton)
                .height(deleteButton.getLabel().getPrefHeight() + 10)
                .width(deleteButton.getLabel().getPrefWidth() + 10);

        rootTable.add(lastRow)
                .expandX()
                .fill();
        
        this.addActor(rootTable);
        rootTable.setFillParent(true);
    }

    public void setData(Crate crate) {
        this.data = crate;
        nameLabel.setText(itemData.getItemName(crate.getId()));
        amountLabel.setText(String.format("%sx4", Integer.toString(crate.getQueueNeeded())));
        costLabel.setText(costStringGenerator.generate(itemData.getCost(crate.getId())));
        priorityLabel.setText(crate.getPriority().getReadableName());
    }

    @Override
    public float getPrefHeight() {
        return nameLabel.getPrefHeight() * 3 + outerPadding * 2 + innerPadding * 2;
    }

    @Override
    public float getPrefWidth() {
        float maxWidth = 0;

        for (Actor child : getChildren()) {
            maxWidth = Math.max(maxWidth, child.getWidth());
        }

        return maxWidth;
    }

    public static class ManuItemStyle {
        public Drawable background;

        public ManuItemStyle() {}
    }
}