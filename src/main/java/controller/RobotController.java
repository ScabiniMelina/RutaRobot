package controller;

import model.BoardName;
import model.Grid;
import model.Position;

import java.util.Arrays;
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

    public String[] getBoardNames() {
        return Arrays.stream(BoardName.values())
                .map(BoardName::toString)
                .toArray(String[]::new);
    }
}
