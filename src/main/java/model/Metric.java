package model;

import java.util.concurrent.TimeUnit;

public class Metric {


    private int exploredPaths;
    private int recursiveCalls;
    private long executionTime;

    public void reset() {
        this.exploredPaths = 0;
        this.recursiveCalls = 0;
        this.executionTime = 0;
    }

    public void incrementExploredPaths() {
        this.exploredPaths++;
    }

    public void incrementRecursiveCalls() {
        this.recursiveCalls++;
    }

    public void startTimer() {
        this.executionTime = -System.nanoTime();
    }

    public void stopTimer() {
        this.executionTime += System.nanoTime();
    }

    public int getExploredPaths() {
        return exploredPaths;
    }

    public int getRecursiveCalls() {
        return recursiveCalls;
    }

    public double getExecutionTimeMs() {
        return TimeUnit.NANOSECONDS.toMillis(executionTime);
    }

    @Override
    public String toString() {
        return String.format("Paths: %d, Calls: %d, Time: %.3f ms",
                exploredPaths, recursiveCalls, getExecutionTimeMs());
    }
}
