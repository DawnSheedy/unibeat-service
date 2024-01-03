package com.dawnsheedy.model;

public enum GameDataType {
    Banner("Banner"),
    IndexTrack("IndexTrack"),
    Track("Track");


    public final String label;
    private GameDataType(String type) {
        this.label = type;
    }
}
