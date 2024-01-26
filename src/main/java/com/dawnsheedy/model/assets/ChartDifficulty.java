package com.dawnsheedy.model.assets;

public enum ChartDifficulty {
    Basic("Basic"),
    Advanced("Advanced"),
    Extreme("Extreme"),
    Special("Special");


    public final String label;
    private ChartDifficulty(String type) {
        this.label = type;
    }
}
