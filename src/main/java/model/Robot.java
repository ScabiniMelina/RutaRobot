package model;

import observer.IObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Robot {
    private Grid grid;
    private List<Position> bestRoute;
    private int pathsExploredNoPruning;
    private int pathsExploredPruning;
    private long executionTimeNoPruning;
    private long executionTimePruning;
    private List<IObserver> observers;
    private final int lastRow;
    private final int lastColumn;

    public Robot(Grid grid) {
        this.grid = grid;
        this.bestRoute = null;
        this.pathsExploredNoPruning = 0;
        this.pathsExploredPruning = 0;
        this.executionTimeNoPruning = 0;
        this.executionTimePruning = 0;
        this.lastRow = grid.getRows() - 1;
        this.lastColumn =  grid.getColumns() -1;

        this.observers = new ArrayList<>();
    }

    public void addObserver(IObserver observer) {
        this.observers.add(observer);
    }

    public List<Position> getBestRouteWithoutPruning() throws Exception {
        long startTime = System.nanoTime();
        pathsExploredNoPruning = 0;
        bestRoute = null;
        List<Position> currentPath = new ArrayList<>();
        findPathWithoutPruning(0, 0, 0, currentPath);
        executionTimeNoPruning = System.nanoTime() - startTime;
        if (bestRoute == null) {
            throw new Exception("No valid path found with sum zero (without pruning)");
        }
        notifyObservers();
        return bestRoute;
    }

    public List<Position> getBestRouteWithPruning() throws Exception {
        long startTime = System.nanoTime();
        pathsExploredPruning = 0;
        bestRoute = null;
        List<Position> currentPath = new ArrayList<>();
        findPathWithPruning(0, 0, 0, currentPath);
        executionTimePruning = System.nanoTime() - startTime;
        if (bestRoute == null) {
            throw new Exception("No valid path found with sum zero (with pruning)");
        }
        notifyObservers();
        return bestRoute;
    }

    private boolean findPathWithoutPruning(int x, int y, int sum, List<Position> currentPath) {
        currentPath.add(new Position(x, y));
        sum += grid.getGrid()[x][y];
        pathsExploredNoPruning++;

        // Condición de éxito
        if (x == lastRow  && y == lastColumn ) {
            if (sum == 0) {
                bestRoute = new ArrayList<>(currentPath);
                return true;
            }
            currentPath.remove(currentPath.size() - 1);
            return false;
        }

        boolean found = false;

        // Movimiento a la derecha
        if (y + 1 <= lastColumn ) {
            found |= findPathWithoutPruning(x, y + 1, sum, currentPath);
        }

        // Movimiento hacia abajo
        if (!found && x + 1 <= lastRow ) {
            found |= findPathWithoutPruning(x + 1, y, sum, currentPath);
        }

        currentPath.remove(currentPath.size() - 1);
        return found;
    }

    private boolean findPathWithPruning(int x, int y, int sum, List<Position> currentPath) {
        currentPath.add(new Position(x, y));
        sum += grid.getGrid()[x][y];
        pathsExploredPruning++;

        // Condición de éxito
        if (x == lastRow && y == lastColumn) {
            if (sum == 0) {
                bestRoute = new ArrayList<>(currentPath);
                return true;
            }
            currentPath.remove(currentPath.size() - 1);
            return false;
        }

        // Poda: verificar si la suma puede llegar a 0
        int stepsLeft = (lastRow - x) + (lastColumn - y);
        //cuántos pasos faltan para llegar al destino (suma de movimientos abajo y a la derecha).
        // sum + stepsLeft < 0: Si incluso con todos +1, la suma es negativa, no sirve.
        //sum - stepsLeft > 0: Si incluso con todos -1, la suma es positiva, no sirve.
        if (sum + stepsLeft < 0 || sum - stepsLeft > 0) {
            currentPath.remove(currentPath.size() - 1);
            return false;
        }

        boolean found = false;

        // Movimiento a la derecha
        if (y + 1 <= lastColumn ) {
            found |= findPathWithoutPruning(x, y + 1, sum, currentPath);
        }

        // Movimiento hacia abajo
        if (!found && x + 1 <= lastRow ) {
            found |= findPathWithoutPruning(x + 1, y, sum, currentPath);
        }

        currentPath.remove(currentPath.size() - 1);
        return found;
    }

    public int getPathsExploredNoPruning() {
        return pathsExploredNoPruning;
    }

    public int getPathsExploredPruning() {
        return pathsExploredPruning;
    }

    public double getExecutionTimeNoPruningMs() {
        return executionTimeNoPruning / 1_000_000.0;
    }

    public double getExecutionTimePruningMs() {
        return executionTimePruning / 1_000_000.0;
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

}