package model;

import observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private int[][] grid;
    private List<Observer> observers;

    public Grid(int[][] grid) {
        isValidGrid(grid);
        this.grid = grid;
        this.observers = new ArrayList<Observer>();
    }

    public void addObserver(Observer observer){
        if (observer == null)
            throw new RuntimeException("The observer cannot be null");
        this.observers.add(observer);
    }

    private void isValidGrid(int[][] grid) {
        hasValidSize(grid);
        hasValidNumber(grid);
    }

    private void hasValidSize(int[][] grid) {
        if (grid == null) {
            throw new IllegalArgumentException("Grid cannot be null");
        }
        int rows = grid.length;
        int cols = grid[0].length;
        int pathLength = rows + cols - 1;
        if (pathLength % 2 != 0) {
            throw new IllegalArgumentException(
                    "Path length " + pathLength + " is odd; must be even"
            );
        }
    }

    private void hasValidNumber(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int value = grid[i][j];
                if (value != 1 && value != -1) {
                    throw new IllegalArgumentException(
                            "Invalid value at [" + i + "][" + j + "]: " + value +
                                    "; only +1 or -1 allowed"
                    );
                }
            }
        }
    }

}
