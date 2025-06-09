package model;

import observer.IObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Robot {
    private Grid grid;
    private List<List<Position>> allValidPaths;
    private List<IObserver> observers;
    private final int lastRow;
    private final int lastColumn;
    private Metric nonPruningMetric;
    private Metric pruningMetric;

    public Robot(Grid grid) {
        if (grid == null) {
            throw new IllegalArgumentException("Grid cannot be null");
        }
        this.grid = grid;
        this.lastRow = grid.getRows() - 1;
        this.lastColumn = grid.getColumns() - 1;
        this.allValidPaths = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.nonPruningMetric = new Metric();
        this.pruningMetric = new Metric();
    }

    public void addObserver(IObserver observer) {
        this.observers.add(observer);
    }

    public List<List<Position>> getBestRouteWithoutPruning() throws Exception {
        nonPruningMetric.reset();
        nonPruningMetric.startTimer();
        allValidPaths.clear();
        List<Position> currentPath = new ArrayList<>();
        currentPath.add(new Position(0, 0));
        findPathWithoutPruning(0, 0, grid.getGrid()[0][0], currentPath);
        nonPruningMetric.stopTimer();
        notifyObservers();
        return new ArrayList<>(allValidPaths);
    }

    public List<List<Position>> getBestRouteWithPruning() throws Exception {
        pruningMetric.reset();
        pruningMetric.startTimer();
        allValidPaths.clear();
        List<Position> currentPath = new ArrayList<>();
        currentPath.add(new Position(0, 0));
        findPathWithPruning(0, 0, grid.getGrid()[0][0], currentPath);
        pruningMetric.stopTimer();
        notifyObservers();
        return new ArrayList<>(allValidPaths);
    }

    private void findPathWithoutPruning(int x, int y, int sum, List<Position> currentPath) {
        nonPruningMetric.incrementRecursiveCalls();

        if (isAtDestination(x, y)) {
            nonPruningMetric.incrementExploredPaths();
            if (sum == 0) {
                allValidPaths.add(new ArrayList<>(currentPath));
            }
            return;
        }

        if (canMoveRight(y)) {
            currentPath.add(new Position(x, y + 1));
            findPathWithoutPruning(x, y + 1, sum + grid.getGrid()[x][y + 1], currentPath);
            currentPath.remove(currentPath.size() - 1);
        }

        if (canMoveDown(x)) {
            currentPath.add(new Position(x + 1, y));
            findPathWithoutPruning(x + 1, y, sum + grid.getGrid()[x + 1][y], currentPath);
            currentPath.remove(currentPath.size() - 1);
        }
    }

    private void findPathWithPruning(int x, int y, int sum, List<Position> currentPath) {
        pruningMetric.incrementRecursiveCalls();

        if (isAtDestination(x, y)) {
            pruningMetric.incrementExploredPaths();
            if (sum == 0) {
                allValidPaths.add(new ArrayList<>(currentPath));
            }
            return;
        }


        int stepsLeft = (lastRow - x) + (lastColumn - y);

        // Poda por paridad: si los pasos restantes más la suma actual no permiten balancear a cero (paridad)
        // Es decir, si la suma y los pasos restantes tienen distinta paridad, no puede llegar a cero
        //if ((stepsLeft + sum) % 2 != 0) { return; }

        if (isBalanceImpossible(sum, stepsLeft)) {
           return;
        }

        if (canMoveRight(y)) {
            currentPath.add(new Position(x, y + 1));
            findPathWithPruning(x, y + 1, sum + grid.getGrid()[x][y + 1], currentPath);
            currentPath.remove(currentPath.size() - 1);
        }

        if (canMoveDown(x)) {
            currentPath.add(new Position(x + 1, y));
            findPathWithPruning(x + 1, y, sum + grid.getGrid()[x + 1][y], currentPath);
            currentPath.remove(currentPath.size() - 1);
        }
    }

    public boolean isAtDestination(int x, int y) {
        return x == lastRow && y == lastColumn;
    }

    public boolean canMoveRight(int y) {
        return y < lastColumn;
    }

    public boolean canMoveDown(int x) {
        return x < lastRow;
    }

    private boolean isBalanceImpossible(int sum, int stepsLeft) {
        return Math.abs(sum) > stepsLeft;
    }


    public Metric getNonPruningMetrics() {
        return nonPruningMetric;
    }

    public Metric getPruningMetrics() {
        return pruningMetric;
    }

    private void notifyObservers() {
        for (IObserver observer : observers) {
            observer.notify(this.grid);
        }
    }

    public String formatPaths(List<List<Position>> paths, String label) {
        if (paths == null || paths.isEmpty()) {
            return label + ": No valid paths found";
        }
        StringBuilder result = new StringBuilder(label + ":\n");
        for (int i = 0; i < paths.size(); i++) {
            List<Position> path = paths.get(i);
            String pathStr = path.stream()
                    .map(p -> String.format("(%d,%d)", p.getX(), p.getY()))
                    .collect(Collectors.joining(" -> "));
            result.append(String.format("Path %d: %s\n", i + 1, pathStr));
        }
        return result.toString();
    }

    public Grid getGrid() {
        return grid;
    }
}