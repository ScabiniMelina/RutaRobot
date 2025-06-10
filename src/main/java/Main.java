import controller.RobotController;
import model.Grid;

import model.Position;
import model.Robot;
import model.util.*;
import view.GameView;
import view.MainView;
import view.ReportView;

import java.io.IOException;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
//        System.out.printf("Hello and welcome!");
//        String filePath = "single_zero_path_5x6.json";
//        int[][] gridValues  = JsonReader.readGridFromJSON(filePath);
//        Grid grid = new Grid(gridValues);
//        System.out.print(grid.toString());
//        Robot robot = new Robot(grid);
//        List<Position> positions1 = robot.getBestRouteWithPruning();
//        System.out.print(robot.formatPath(positions1,"test1 poda"));
//        System.out.print("\n");
//        List<Position> positions = robot.getBestRouteWithoutPruning();
//        System.out.print(robot.formatPath(positions,"test fuerza bruta"));


        Grid grid = new Grid();
        RobotController controller = new RobotController(grid);
        MainView mainView = new MainView(controller);
        GameView gameView = new GameView(controller);
        ReportView reportView = new ReportView(controller);
        mainView.setVisible(true);
        //int[][] jsonGrid = JsonReader.readGridFromJSON("");
    }
}