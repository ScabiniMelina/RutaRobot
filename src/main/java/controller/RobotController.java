package controller;

import model.Grid;
import model.Position;

import java.util.List;

public class RobotController {
    private Grid grid;

    public RobotController(Grid grid) {
        this.grid = grid;
    }

    public int[][] getGrid() {
        return grid.getGrid();
    }

    public List<Position> getBestRoute() throws Exception {
        return grid.getBestRoute();
    }
}
