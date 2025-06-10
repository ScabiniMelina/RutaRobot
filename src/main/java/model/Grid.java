package model;

import observer.IObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static view.util.Texts.GRID_UPDATED;

public class Grid {
    private int[][] gridMatrix;
    private List<IObserver> observers;

    public Grid(int[][] grid) {
        this.observers = new ArrayList<>();
    }

    public void initGrid(int[][] grid) {
        validateGrid(grid);
        this.gridMatrix = grid;
        notifyObservers(GRID_UPDATED);
    }

    public Grid() {
        Random random = new Random();

        int pathLength = generateRandomEvenPathLength(random); // Generar pathLength par
        int totalGrid = pathLength + 1;
        int rows = totalGrid / 2;
        int cols = totalGrid - rows;

        System.out.println("pathLength: " + pathLength);
        System.out.println("rows: " + rows + ", cols: " + cols);

        int[][] gridData = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gridData[i][j] = random.nextBoolean() ? 1 : -1;
            }
        }

        this.gridMatrix = gridData;
        validateGrid(gridMatrix);
        this.observers = new ArrayList<IObserver>();
    }

    public void addObserver(IObserver observer) {
        if (isObserverNull(observer))
            throw new RuntimeException("The observer cannot be null");
        this.observers.add(observer);
    }

    public List<IObserver> getObservers() {
        return observers;
    }

    public int[][] getGrid() {
        return this.gridMatrix;
    }

    public int getRows() {
        return gridMatrix.length;
    }

    public int getColumns() {
        return gridMatrix[0].length;
    }

    private void validateGrid(int[][] grid) {
        validateGridSize(grid);
        validateGridValues(grid);
    }

    private void validateGridSize(int[][] grid) {
        if (isGridNull(grid)) {
            throw new IllegalArgumentException("Grid cannot be null");
        }
        int rows = grid.length;
        int cols = grid[0].length;
        int pathLength = rows + cols - 1;
        if (isPathLengthOdd(pathLength)) {
            throw new IllegalArgumentException(
                    "Path length " + pathLength + " is odd; must be even"
            );
        }
    }

    private void validateGridValues(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int value = grid[i][j];
                if (isInvalidGridValue(value)) {
                    throw new IllegalArgumentException(
                            "Invalid value at [" + i + "][" + j + "]: " + value +
                                    "; only +1 or -1 allowed"
                    );
                }
            }
        }
    }

    private boolean isGridNull(int[][] grid) {
        return grid == null;
    }

    private boolean isPathLengthOdd(int pathLength) {
        return pathLength % 2 != 0;
    }

    private boolean isInvalidGridValue(int value) {
        return value != 1 && value != -1;
    }

    private boolean isObserverNull(IObserver observer) {
        return observer == null;
    }

    private int generateRandomEvenPathLength(Random random) {
        return 2 + random.nextInt(5) * 2; // 2, 4, 6, ..., 18
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Grid ").append(":\n");
        for (int i = 0; i < gridMatrix.length; i++) {
            for (int j = 0; j < gridMatrix[i].length; j++) {
                sb.append(gridMatrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private void notifyObservers(String eventType) {
        for (IObserver observer : observers) {
            observer.update(eventType);
        }
    }
}
