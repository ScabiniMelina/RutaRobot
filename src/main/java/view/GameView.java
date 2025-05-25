package view;

import controller.RobotController;
import model.Grid;
import observer.IObserver;

import javax.swing.*;

public class GameView extends JFrame implements IObserver {
    private final RobotController robotController;

    public GameView(RobotController robotController){
        this.robotController = robotController;
    }

    @Override
    public void notify(Grid grid) {

    }
}
