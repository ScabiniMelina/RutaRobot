package view;

import controller.RobotController;
import observer.IObserver;


public class ReportView extends BaseView implements IObserver {
    private final RobotController robotController;

    public ReportView(RobotController robotController) {
        this.robotController = robotController;
    }

    @Override
    public void update(String eventType) {

    }
}
