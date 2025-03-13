package com.lunaciadev.choreographer.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;
import com.lunaciadev.choreographer.core.Choreographer;
import com.lunaciadev.choreographer.data.UIDataPackage;

public class ProgressDisplay {
    private VerticalGroup table;
    private Label queuedOrdersLabel;
    private Label progressLabel;

    private Skin skin;

    public ProgressDisplay(UIDataPackage uiDataPackage) {
        this.skin = uiDataPackage.getSkin();

        setLayout();
    }

    private void setLayout() {
        table = new VerticalGroup();
        table.grow().space(10);

        queuedOrdersLabel = new Label("0 truck in queue.", skin);
        progressLabel = new Label("0.00% total crates made/queued.", skin);

        queuedOrdersLabel.setAlignment(Align.center);
        progressLabel.setAlignment(Align.center);

        table.addActor(queuedOrdersLabel);
        table.addActor(progressLabel);
    }

    public VerticalGroup getWidget() {
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
