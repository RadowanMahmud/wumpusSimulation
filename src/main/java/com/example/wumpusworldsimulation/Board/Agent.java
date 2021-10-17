package com.example.wumpusworldsimulation.Board;

import java.util.ArrayList;
import java.util.Arrays;

public class Agent {
    private int currentRow= 0;
    private int currentCol= 0;
    private String currentDirection = "down";
    public Agent(){

    }


    public int getCurrentCol() {
        return currentCol;
    }

    public void setCurrentCol(int currentCol) {
        this.currentCol = currentCol;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(String currentDirection) {
        this.currentDirection = currentDirection;
    }
}
