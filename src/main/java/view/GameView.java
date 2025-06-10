package view;

import controller.RobotController;
import model.Grid;
import observer.IObserver;


public class GameView extends BaseView implements IObserver {
    private final RobotController robotController;

    public GameView(RobotController robotController) {
        this.robotController = robotController;
    }

    public void setSelectedBoard(String selectedBoard) {
        robotController.setRobotGrid(selectedBoard);
    }

    @Override
    public void update(String eventType) {

    }
}
