package view;

import controller.RobotController;
import model.Grid;
import observer.IObserver;

import javax.swing.JFrame;

public class ReportView extends JFrame implements IObserver {
    private final RobotController robotController;

    public ReportView(RobotController robotController) {
        this.robotController = robotController;
    }


    @Override
    public void notify(Grid grid) {

    }
}
