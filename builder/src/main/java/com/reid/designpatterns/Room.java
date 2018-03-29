package com.reid.designpatterns;

public class Room {
    private String window;
    private String floor;
    private String cateen;

    public Room apply(WorkBuild.RoomParams params) {
        window = params.window;
        floor = params.floor;
        cateen = params.cateen;

        return this;
    }

    public String toString() {
        return "floor: " + floor + ", window: " + window;
    }
}
