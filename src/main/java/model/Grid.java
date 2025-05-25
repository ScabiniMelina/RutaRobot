package model;

import observer.IObserver;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private int[][] grid;
    private List<IObserver> observers;

    public Grid(int[][] grid) {
        isValidGrid(grid);
        this.grid = grid;
        this.observers = new ArrayList<IObserver>();
    }

    public void addObserver(IObserver observer){
        if (observer == null)
            throw new RuntimeException("The observer cannot be null");
        this.observers.add(observer);
    }

    public List<IObserver> getObservers() {
        return observers;
    }

    public int[][] getGrid(){
        return this.grid;
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
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Grid ").append(":\n");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                sb.append(grid[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
