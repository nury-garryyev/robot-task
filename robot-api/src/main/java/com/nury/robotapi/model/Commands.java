package com.nury.robotapi.model;

import java.util.List;

public class Commands {

    private int gridWidth;
    private int gridHeight;
    private List<String> commands;

    public Commands() {
    }

    public Commands(int gridWidth, int gridHeight, List<String> commands) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.commands = commands;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    @Override
    public String toString() {
        return "Commands{" +
                "gridWidth=" + gridWidth +
                ", gridHeight=" + gridHeight +
                ", commands=" + commands +
                '}';
    }
}
