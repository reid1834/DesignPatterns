package com.reid.designpatterns;

public class WorkBuild {
    Room room = new Room();
    private RoomParams params = new RoomParams();

    public WorkBuild makeWindow(String window) {
        params.window = window;

        return this;
    }

    public WorkBuild makeFloor(String floor) {
        params.floor = floor;

        return this;
    }

    public Room build() {
        room.apply(params);

        return room;
    }

    class RoomParams {
        public String window;
        public String floor;
        public String cateen;
    }
}
