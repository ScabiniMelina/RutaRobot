package model;

import observer.IObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Robot {
    private Grid grid;
    private List<Position> bestRoute;
    private List<IObserver> observers;

    private final int lastRow;
    private final int lastColumn;

    private Metric nonPruningMetric;
    private Metric pruningMetric = new Metric();


    public Robot(Grid grid) {
        if (grid == null) {
            throw new IllegalArgumentException("Grid cannot be null");
        }
        this.grid = grid;
        this.lastRow = grid.getRows() - 1;
        this.lastColumn =  grid.getColumns() -1;

        this.bestRoute = null;
        this.observers = new ArrayList<>();

        this.nonPruningMetric = new Metric();
        this.pruningMetric = new Metric();

        this.pruningMetric.reset();

    }

    public void addObserver(IObserver observer) {
        this.observers.add(observer);
    }

    public List<Position> getBestRouteWithoutPruning() throws Exception {
        this.nonPruningMetric.reset();
        this.nonPruningMetric.startTimer();
        bestRoute = null;
        List<Position> currentPath = new ArrayList<>();
        findPathWithoutPruning(0, 0, 0, currentPath);
        this.nonPruningMetric.stopTimer();
        notifyObservers();
        return bestRoute;
    }

    public List<Position> getBestRouteWithPruning() throws Exception {
        this.pruningMetric.reset();
        this.pruningMetric.startTimer();
        bestRoute = null;
        List<Position> currentPath = new ArrayList<>();
        findPathWithPruning(0, 0, 0, currentPath);
        this.pruningMetric.stopTimer();
        notifyObservers();
        return bestRoute;
    }

    private boolean findPathWithoutPruning(int x, int y, int sum, List<Position> currentPath) {
        currentPath.add(new Position(x, y));
        sum += grid.getGrid()[x][y];
        nonPruningMetric.incrementRecursiveCalls();

        // Condición de éxito
        if (isAtDestination(x,y) ) {
            nonPruningMetric.incrementExploredPaths();
            if (sum == 0) {
                bestRoute = new ArrayList<>(currentPath);
                return true;
            }
            currentPath.remove(currentPath.size() - 1);
            return false;
        }

        boolean found = false;

        if (canMoveRight(y)) {
            found |= findPathWithoutPruning(x, y + 1, sum, currentPath);
        }

        if (canMoveDown(x)) {
            found |= findPathWithoutPruning(x + 1, y, sum, currentPath);
        }

        currentPath.remove(currentPath.size() - 1);
        return found;
    }

    private boolean findPathWithPruning(int x, int y, int sum, List<Position> currentPath) {
        currentPath.add(new Position(x, y));
        sum += grid.getGrid()[x][y];
        pruningMetric.incrementRecursiveCalls();

        if (isAtDestination(x,y)) {
            pruningMetric.incrementExploredPaths();
            if (sum == 0) {
                bestRoute = new ArrayList<>(currentPath);
                return true;
            }
            currentPath.remove(currentPath.size() - 1);
            return false;
        }

        // Poda
        if (!canReachZeroSum(x,y,sum)) {
            pruningMetric.incrementExploredPaths();
            currentPath.remove(currentPath.size() - 1);
            return false;
        }

        boolean found = false;


        if (canMoveRight(y)) {
            found |= findPathWithPruning(x, y + 1, sum, currentPath);
        }

        if (canMoveDown(x)) {
            found |= findPathWithPruning(x + 1, y, sum, currentPath);
        }

        currentPath.remove(currentPath.size() - 1);
        return found;
    }

    public boolean isAtDestination(int x, int y) {
        return x == lastRow && y == lastColumn;
    }

    private boolean canReachZeroSum(int x, int y, int sum) {
        //cuántos pasos faltan para llegar al destino (suma de movimientos abajo y a la derecha).
        // sum + stepsLeft < 0: Si incluso con todos +1, la suma es negativa, no sirve.
        //sum - stepsLeft > 0: Si incluso con todos -1, la suma es positiva, no sirve.
        int stepsLeft = (lastRow - x) + (lastColumn - y);
        return sum + stepsLeft >= 0 && sum - stepsLeft <= 0;
    }

    public boolean canMoveRight(int y) {
        return y + 1 <= lastColumn;
    }

    public boolean canMoveDown(int x) {
        return x + 1 <= lastRow;
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


    public String formatPath(List<Position> path, String label) {
        String pathStr = path.stream()
                .map(p -> String.format("(%d,%d)", p.getX(), p.getY()))
                .collect(Collectors.joining(" -> "));
        return String.format("%s: %s", label, pathStr);
    }

    public Grid getGrid() {
        return grid;
    }
}