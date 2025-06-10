package controller;

import model.BoardName;
import model.Position;
import model.Robot;
import model.util.JsonReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RobotController {
    private Robot robot;

    public RobotController(Robot robot) {
        this.robot = robot;
    }

    public int[][] getRobotGrid() {
        return robot.getGrid().getGrid();
    }

    public void setRobotGrid(String boardName) {
        String path = BoardName.getPathByName(boardName);
        
        if (path == null) {
            System.err.println("No se encontró un tablero con el nombre: " + boardName);
            return;
        }
        
        int[][] jsonGrid;
        try {
            jsonGrid = JsonReader.readGridFromJSON(path);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo " + path + ": " + e.getMessage());
            return;
        }

        if (jsonGrid != null) {
            robot.getGrid().initGrid(jsonGrid);
        }
    }

    public List<Position> getBestRouteWithoutPruning(int index) throws Exception {
        return robot.getRoutesWithoutPruning().get(index);
    }

    public List<Position> getBestRoutesWithPruning(int index) throws Exception {
        return robot.getRoutesWithPruning().get(index);
    }

    public String[] getBoardNames() {
        return Arrays.stream(BoardName.values())
                .map(BoardName::toString)
                .toArray(String[]::new);
    }
}
