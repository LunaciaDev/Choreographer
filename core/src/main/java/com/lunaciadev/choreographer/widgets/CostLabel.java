package com.lunaciadev.choreographer.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.lunaciadev.choreographer.data.UIDataPackage;
import com.lunaciadev.choreographer.types.Cost;
import com.lunaciadev.choreographer.utils.CostStringGenerator;

public class CostLabel {
    private Label costLabel;
    private CostStringGenerator costStringGenerator;

    public CostLabel(UIDataPackage uiDataPackage) {
        this.costLabel = new Label("[PH]", uiDataPackage.getSkin());
        this.costStringGenerator = new CostStringGenerator();
    }

    public Label getLabel() {
        return costLabel;
    }

    /**
     * Slot, to be added later
     */
    public void onTruckSubmitted(Object... args) {
        Cost cost = (Cost) args[2];
        costLabel.setText(costStringGenerator.generate(cost));
    }

    /**
     * Slot, to be added later
     */
    public void onItemQueued(Object... args) {
        if (!(boolean) args[0]) return;

        Cost cost = (Cost) args[1];
        costLabel.setText(costStringGenerator.generate(cost));
    }
}
