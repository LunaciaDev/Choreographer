package com.lunaciadev.choreographer.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.badlogic.gdx.Gdx;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.lunaciadev.choreographer.types.EventType;
import com.lunaciadev.choreographer.types.QueueType;

public class GlobalKeyListener implements NativeKeyListener {
    private Set<Integer> heldKeys;
    private boolean keyLock;

    public Signal keyEvent = new Signal();

    public GlobalKeyListener() {
        heldKeys = new HashSet<>();
        keyLock = false;
    }

    public boolean activateListener() {
        try {
            Logger.getLogger(GlobalScreen.class.getPackageName()).setLevel(Level.OFF);
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean deactivateListener() {
        try {
            GlobalScreen.unregisterNativeHook();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        int keyCode = nativeEvent.getKeyCode();

        if (heldKeys.contains(NativeKeyEvent.VC_CONTROL) && !keyLock && !(heldKeys.size() >= 2)) {
            switch (keyCode) {
                case NativeKeyEvent.VC_1:
                    broadcastKeyEvent(EventType.QUEUE_ORDER, QueueType.LIGHT_ARMS);
                    keyLock = true;
                    break;

                case NativeKeyEvent.VC_2:
                    broadcastKeyEvent(EventType.QUEUE_ORDER, QueueType.HEAVY_ARMS);
                    keyLock = true;
                    break;

                case NativeKeyEvent.VC_3:
                    broadcastKeyEvent(EventType.QUEUE_ORDER, QueueType.HEAVY_AMMO);
                    keyLock = true;
                    break;

                case NativeKeyEvent.VC_4:
                    broadcastKeyEvent(EventType.QUEUE_ORDER, QueueType.UTILITIES);
                    keyLock = true;
                    break;

                case NativeKeyEvent.VC_5:
                    broadcastKeyEvent(EventType.QUEUE_ORDER, QueueType.MEDICAL);
                    keyLock = true;
                    break;

                case NativeKeyEvent.VC_6:
                    broadcastKeyEvent(EventType.QUEUE_ORDER, QueueType.UNIFORMS);
                    keyLock = true;
                    break;

                case NativeKeyEvent.VC_Z:
                    broadcastKeyEvent(EventType.UNDO, null);
                    keyLock = true;
                    break;

                case NativeKeyEvent.VC_ENTER:
                    broadcastKeyEvent(EventType.TRUCK_SUBMIT, null);
                    keyLock = true;
                    break;

                case NativeKeyEvent.VC_Q:
                    broadcastKeyEvent(EventType.QUIT, null);
            }
        }

        heldKeys.add(nativeEvent.getKeyCode());
        Gdx.app.log("Key Press", NativeKeyEvent.getKeyText(nativeEvent.getKeyCode()));
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
        int keyCode = nativeEvent.getKeyCode();

        switch (keyCode) {
            case NativeKeyEvent.VC_1:
            case NativeKeyEvent.VC_2:
            case NativeKeyEvent.VC_3:
            case NativeKeyEvent.VC_4:
            case NativeKeyEvent.VC_5:
            case NativeKeyEvent.VC_6:
            case NativeKeyEvent.VC_Z:
            case NativeKeyEvent.VC_ENTER:
            case NativeKeyEvent.VC_CONTROL:
                keyLock = false;
        }

        heldKeys.remove(keyCode);
        Gdx.app.log("Key Release", NativeKeyEvent.getKeyText(nativeEvent.getKeyCode()));
    }

    private void broadcastKeyEvent(EventType eventType, QueueType queueType) {
        keyEvent.emit(eventType, queueType);
    }
}
