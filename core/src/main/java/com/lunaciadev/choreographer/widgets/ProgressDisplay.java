package com.lunaciadev.choreographer.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lunaciadev.choreographer.core.Choreographer;
import com.lunaciadev.choreographer.data.UIDataPackage;

public class ProgressDisplay {
    private HorizontalGroup table;
    private Label queuedOrdersLabel;
    private Label progressLabel;

    private Skin skin;

    public ProgressDisplay(UIDataPackage uiDataPackage) {
        this.skin = uiDataPackage.getSkin();

        setLayout();
    }

    private void setLayout() {
        table = new HorizontalGroup();
        table.grow();

        queuedOrdersLabel = new Label("[PH]", skin);
        progressLabel = new Label("[PH]", skin);

        table.addActor(queuedOrdersLabel);
        table.addActor(progressLabel);
    }

    public HorizontalGroup getWidget() {
        return table;
    }

    /**
     * Slot, triggered by {@link Choreographer#truckSubmitted}
     */
    public void onTruckSubmitted(Object... args) {
        updateQueue((int) args[0]);
        updateProgress((float) args[1]);
    }

    private void updateQueue(int queueSize) {
        if (queueSize > 2) {
            queuedOrdersLabel.setText(queueSize + " orders queued.");
        }
        else {
            queuedOrdersLabel.setText(queueSize + " order queued.");
        }
    }

    private void updateProgress(float progress) {
        progressLabel.setText(progress + "% crate manufactured.");
    }
}
