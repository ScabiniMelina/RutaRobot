package controller;

import model.BoardName;
import model.Position;
import model.Robot;
import model.util.JsonReader;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RobotController {
    private Robot robot;

    public RobotController(Robot robot) {
        this.robot = robot;
    }

    public int[][] getRobotGrid() {
        return robot.getGrid().getGrid();
    }

    public void setRobotGrid(String boardName) {
        if (boardName.equals("RANDOM")) {
            robot.getGrid().generateRandomGrid();
            return;
        }
        
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

    public void addGridObserver(observer.IObserver observer) {
        robot.getGrid().addObserver(observer);
    }

    public List<Point> getBestRouteWithoutPruning(int index) {
        List<Position> modelPath = robot.getRoutesWithoutPruning().get(index);
        return convertToPoints(modelPath);
    }

    public List<Point> getBestRoutesWithPruning(int index) {
        List<Position> modelPath = new ArrayList<>();
            modelPath = robot.getRoutesWithPruning().get(index);
        return convertToPoints(modelPath);
    }

    private List<Point> convertToPoints(List<Position> positions) {
        return positions.stream()
                .map(pos -> new Point(pos.getX(), pos.getY()))
                .collect(Collectors.toList());
    }

    public String[] getBoardNames() {
        return Arrays.stream(BoardName.values())
                .map(BoardName::toString)
                .toArray(String[]::new);
    }
}
