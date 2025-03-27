package com.lunaciadev.choreographer.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.lunaciadev.choreographer.core.Choreographer;
import com.lunaciadev.choreographer.data.UIDataPackage;
import com.lunaciadev.choreographer.types.QueueType;

public class QueueTypeHighlight {
    private Table group;

    private TextButton lightArmButton;
    private TextButton heavyArmButton;
    private TextButton heavyShellButton;
    private TextButton utilitiesButton;
    private TextButton medicalButton;
    private TextButton uniformButton;

    private final Color ongoingColor = Color.GREEN;
    private final Color finishedColor = Color.RED;

    public QueueTypeHighlight(UIDataPackage uiDataPackage, Choreographer choreographer) {
        lightArmButton = new TextButton("LA", uiDataPackage.getSkin(), "no-highlight");
        heavyArmButton = new TextButton("HA", uiDataPackage.getSkin(), "no-highlight");
        heavyShellButton = new TextButton("HS", uiDataPackage.getSkin(), "no-highlight");
        utilitiesButton = new TextButton("UT", uiDataPackage.getSkin(), "no-highlight");
        medicalButton = new TextButton("ME", uiDataPackage.getSkin(), "no-highlight");
        uniformButton = new TextButton("UN", uiDataPackage.getSkin(), "no-highlight");
        group = new Table();

        lightArmButton.setColor(ongoingColor);
        heavyArmButton.setColor(ongoingColor);
        heavyShellButton.setColor(ongoingColor);
        utilitiesButton.setColor(ongoingColor);
        medicalButton.setColor(ongoingColor);
        uniformButton.setColor(ongoingColor);

        lightArmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                choreographer.queueRequest(QueueType.LIGHT_ARMS);
            }
        });
        
        heavyArmButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                choreographer.queueRequest(QueueType.HEAVY_ARMS);
            }
        });
        
        heavyShellButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                choreographer.queueRequest(QueueType.HEAVY_SHELL);
            }
        });
        
        utilitiesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                choreographer.queueRequest(QueueType.UTILITIES);
            }
        });
        
        medicalButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                choreographer.queueRequest(QueueType.MEDICAL);
            }
        });
        
        uniformButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                choreographer.queueRequest(QueueType.UNIFORMS);
            }
        });

        setLayout();
    }

    private void setLayout() {
        group.defaults().space(8).height(lightArmButton.getLabel().getPrefHeight() + 10);

        group.add(lightArmButton).width(lightArmButton.getLabel().getPrefWidth() + 10);
        group.add(heavyArmButton).width(heavyArmButton.getLabel().getPrefWidth() + 10);
        group.add(heavyShellButton).width(heavyShellButton.getLabel().getPrefWidth() + 10);
        group.add(utilitiesButton).width(utilitiesButton.getLabel().getPrefWidth() + 10);
        group.add(medicalButton).width(medicalButton.getLabel().getPrefWidth() + 10);
        group.add(uniformButton).width(uniformButton.getLabel().getPrefWidth() + 10);
    }

    /**
     * Slot, connected to {@link Choreographer#queueCompleted}
     */
    public void setBackground(Object... args) {
        QueueType queueType = (QueueType) args[0];
        boolean isCompleted = (boolean) args[1];

        TextButton button;

        switch (queueType) {
            case HEAVY_SHELL:
                button = heavyShellButton;
                break;
            case HEAVY_ARMS:
                button = heavyArmButton;
                break;
            case LIGHT_ARMS:
                button = lightArmButton;
                break;
            case MEDICAL:
                button = medicalButton;
                break;
            case UNIFORMS:
                button = uniformButton;
                break;
            case UTILITIES:
                button = utilitiesButton;
                break;
            default:
                button = null;
                break;
        }

        if (isCompleted) {
            button.setColor(finishedColor);
        }
        else {
            button.setColor(ongoingColor);
        }

        button.setDisabled(isCompleted);
    }

    public Table getWidget() {
        return group;
    }
}
