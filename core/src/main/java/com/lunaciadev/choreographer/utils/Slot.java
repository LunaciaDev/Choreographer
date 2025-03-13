package com.lunaciadev.choreographer.utils;

@FunctionalInterface
public interface Slot {
    void onSignal(Object... args);
}