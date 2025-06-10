import controller.RobotController;
import model.Grid;

import model.Robot;
import view.BoardView;
import view.MainView;
import view.ReportView;

public class Main {
    public static void main(String[] args) {

        Grid grid = new Grid();
        Robot robot = new Robot(grid);
        RobotController controller = new RobotController(robot);
        MainView mainView = new MainView(controller);
        BoardView boardView = new BoardView(controller);
        ReportView reportView = new ReportView(controller, boardView);
        mainView.setVisible(true);

    }
}