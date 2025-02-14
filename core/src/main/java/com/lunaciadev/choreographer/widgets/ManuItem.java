package com.lunaciadev.choreographer.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.lunaciadev.choreographer.data.ItemData;
import com.lunaciadev.choreographer.data.UIDataPackage;
import com.lunaciadev.choreographer.types.Cost;
import com.lunaciadev.choreographer.types.Crate;

public class ManuItem extends WidgetGroup {
    private ItemData itemData;
    private Skin skin;
    private Label nameLabel;
    private Label amountLabel;
    private Label costLabel;
    private Label priorityLabel;
    private boolean isFirstItem;
    private Table rootTable;

    // How much space is padded around the widget (not margin, PADDING)
    private final float outerPadding = 10;
    // How much space is padded inbetween row of the widget
    private final float innerPadding = 5;

    public ManuItem(UIDataPackage uiDataPackage, Crate crate) {
        this.itemData = uiDataPackage.getItemData();
        this.skin = uiDataPackage.getSkin();
        setLayout();
        setData(crate);
    }

    public void setLayout() {
        // TODO: remove later
        Pixmap bg = new Pixmap(1, 1, Pixmap.Format.RGB565);
        bg.setColor(Color.BLACK);
        bg.fill();

        rootTable = new Table();
        rootTable.pad(outerPadding);
        rootTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(bg))));

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

        rootTable.add(priorityLabel);
        
        this.addActor(rootTable);
        rootTable.setFillParent(true);
    }

    public void setData(Crate crate) {
        nameLabel.setText(itemData.getItemName(crate.getId()));
        amountLabel.setText(Integer.toString(crate.getQueueNeeded()));
        costLabel.setText(generateCostString(itemData.getCost(crate.getId())));
        
        switch (crate.getPriority()) {
            case 0:
                priorityLabel.setText("High Priority");
                break;
            case 1:
                priorityLabel.setText("Priority");
                break;
            case 2:
                priorityLabel.setText("");
                break;
        }

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
        return this.getParent().getWidth();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}