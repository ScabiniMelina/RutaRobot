import controller.RobotController;
import model.Grid;

import model.Robot;
import view.GameView;
import view.MainView;
import view.ReportView;

public class Main {
    public static void main(String[] args) {

        Grid grid = new Grid();
        Robot robot = new Robot(grid);
        RobotController controller = new RobotController(robot);
        MainView mainView = new MainView(controller);
        GameView gameView = new GameView(controller);
        ReportView reportView = new ReportView(controller);
        mainView.setVisible(true);

    }
}