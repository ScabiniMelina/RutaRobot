package model;

import observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private Cell[][] grid;
    private List<Observer> observers;

    public Grid(Cell[][] grid) {
        this.grid = grid;
        this.observers = new ArrayList<Observer>();
    }

    public void addObserver(Observer observer){
        this.observers.add(observer);
    }
}
