import model.Grid;

import model.Position;
import model.Robot;
import model.util.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");
        String filePath = "single_zero_path_5x6.json";
        int[][] gridValues  = JsonReader.readGridFromJSON(filePath);
        Grid grid = new Grid();
        System.out.print(grid.toString());
        Robot robot = new Robot(grid);
        List<List<Position>> positions1 = robot.getBestRouteWithPruning();
        System.out.print(robot.formatPaths(positions1,"test1 poda"));
        System.out.print("\n");
        List<List<Position>> positions = robot.getBestRouteWithoutPruning();
        System.out.print(robot.formatPaths(positions,"test fuerza bruta"));


    }
}