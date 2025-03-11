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
        table.grow().space(10);

        queuedOrdersLabel = new Label("[PH]", skin);
        progressLabel = new Label("[PH]", skin);

        table.addActor(queuedOrdersLabel);
        table.addActor(progressLabel);
    }

    public HorizontalGroup getWidget() {
        return table;
    }

    /**
     * Slot, triggered by {@link Choreographer#update}
     */
    public void onUpdate(Object... args) {
        updateQueue((int) args[0]);
        updateProgress((float) args[1]);
    }

    private void updateQueue(int queueSize) {
        if (queueSize > 2) {
            queuedOrdersLabel.setText(String.format("%d truck in queue.", queueSize));
        }
        else {
            queuedOrdersLabel.setText(String.format("%d trucks in queue.", queueSize));
        }
    }

    private void updateProgress(float progress) {
        progressLabel.setText(String.format("%.2f%% total crates made/queued.", progress));
    }
}
