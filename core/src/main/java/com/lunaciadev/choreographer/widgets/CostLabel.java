package com.lunaciadev.choreographer.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.lunaciadev.choreographer.core.Choreographer;
import com.lunaciadev.choreographer.data.UIDataPackage;
import com.lunaciadev.choreographer.types.Truck;
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
     * Slot, triggered by {@link Choreographer#truckSubmitted}
     */
    public void onTruckSubmitted(Object... args) {
        Truck truck = (Truck) args[2];
        costLabel.setText(costStringGenerator.generate(truck.getTruckCost()));
    }

    /**
     * Slot, triggered by {@link Choreographer#queueRequestComplete}
     */
    public void onItemQueued(Object... args) {
        if (!(boolean) args[0]) return;

        Truck truck = (Truck) args[1];
        costLabel.setText(costStringGenerator.generate(truck.getTruckCost()));
    }
}
