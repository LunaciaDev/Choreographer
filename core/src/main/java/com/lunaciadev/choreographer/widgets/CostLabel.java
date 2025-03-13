package com.lunaciadev.choreographer.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.lunaciadev.choreographer.core.Choreographer;
import com.lunaciadev.choreographer.data.UIDataPackage;
import com.lunaciadev.choreographer.types.Truck;
import com.lunaciadev.choreographer.utils.CostStringGenerator;

public class CostLabel {
    private HorizontalGroup group;
    private Label costLabel;
    private CostStringGenerator costStringGenerator;

    public CostLabel(UIDataPackage uiDataPackage) {
        this.costLabel = new Label("[PH]", uiDataPackage.getSkin());
        this.costStringGenerator = new CostStringGenerator();
        this.group = new HorizontalGroup();
        
        setLayout(uiDataPackage);
    }

    private void setLayout(UIDataPackage uiDataPackage) {
        group.space(5);
        group.addActor(new Label("Total Cost:", uiDataPackage.getSkin()));
        group.addActor(costLabel);
    }

    public HorizontalGroup getWidget() {
        return group;
    }

    /**
     * Slot, triggered by {@link Choreographer#truckSubmitted}
     */
    public void onTruckSubmitted(Object... args) {
        Truck truck = (Truck) args[0];
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
