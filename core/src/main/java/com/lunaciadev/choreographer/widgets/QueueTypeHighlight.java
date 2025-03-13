package com.lunaciadev.choreographer.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.lunaciadev.choreographer.core.Choreographer;
import com.lunaciadev.choreographer.data.UIDataPackage;
import com.lunaciadev.choreographer.types.QueueType;

public class QueueTypeHighlight {
    private HorizontalGroup group;

    private Label lightArmLabel;
    private Label heavyArmLabel;
    private Label heavyShellLabel;
    private Label utilitiesLabel;
    private Label medicalLabel;
    private Label uniformLabel;

    private final Color finishColor = Color.RED;
    private final Color ongoingColor = Color.GREEN;

    public QueueTypeHighlight(UIDataPackage uiDataPackage) {
        lightArmLabel = new Label("LA", uiDataPackage.getSkin());
        heavyArmLabel = new Label("HA", uiDataPackage.getSkin());
        heavyShellLabel = new Label("HS", uiDataPackage.getSkin());
        utilitiesLabel = new Label("UT", uiDataPackage.getSkin());
        medicalLabel = new Label("ME", uiDataPackage.getSkin());
        uniformLabel = new Label("UN", uiDataPackage.getSkin());
        group = new HorizontalGroup();

        lightArmLabel.setColor(ongoingColor);
        heavyArmLabel.setColor(ongoingColor);
        heavyShellLabel.setColor(ongoingColor);
        utilitiesLabel.setColor(ongoingColor);
        medicalLabel.setColor(ongoingColor);
        uniformLabel.setColor(ongoingColor);

        setLayout();
    }

    private void setLayout() {
        group.space(8);
        group.addActor(lightArmLabel);
        group.addActor(heavyArmLabel);
        group.addActor(heavyShellLabel);
        group.addActor(utilitiesLabel);
        group.addActor(medicalLabel);
        group.addActor(uniformLabel);
    }

    /**
     * Slot, connected to {@link Choreographer#queueCompleted}
     */
    public void setBackground(Object... args) {
        QueueType queueType = (QueueType) args[0];
        boolean isCompleted = (boolean) args[1];

        if (!isCompleted) return;

        switch (queueType) {
            case HEAVY_AMMO:
                heavyShellLabel.setColor(finishColor);
                break;
            case HEAVY_ARMS:
                heavyArmLabel.setColor(finishColor);
                break;
            case LIGHT_ARMS:
                lightArmLabel.setColor(finishColor);
                break;
            case MEDICAL:
                medicalLabel.setColor(finishColor);
                break;
            case UNIFORMS:
                uniformLabel.setColor(finishColor);
                break;
            case UTILITIES:
                utilitiesLabel.setColor(finishColor);
                break;
        }
    }

    public HorizontalGroup getWidget() {
        return group;
    }
}
