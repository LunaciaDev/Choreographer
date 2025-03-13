package com.lunaciadev.choreographer.types;

public enum Priority {
    HIGH_PRIORITY(0, "High Priority"),
    PRIORITY(1, "Priority"),
    NO_PRIORITY(2, "");

    private int id;
    private String humanReadableName;

    private Priority(int id, String humanReadableName) {
        this.id = id;
        this.humanReadableName = humanReadableName;
    }

    public int getId() {
        return id;
    }

    public String getReadableName() {
        return humanReadableName;
    }
}
