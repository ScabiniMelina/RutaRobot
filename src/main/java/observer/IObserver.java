package observer;

import model.Grid;

public interface IObserver {
    public void notify(Grid grid);
}
