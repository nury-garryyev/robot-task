package com.nury.robotapi.model.enumeration;

public enum Direction {
    NORTH(0),
    EAST(1),
    WEST(3),
    SOUTH(2);

    private final int code;

    Direction(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static Direction getByCode(int code) {
        for(Direction e: Direction.values()) {
            if(e.code == code) {
                return e;
            }
        }
        return null;
    }
}
