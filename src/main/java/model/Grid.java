package model;

import observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private Cell[][] grid;
    private List<Observer> observers;

    public Grid(int[][] grid) {
        this.grid = grid;
        this.grid = new Cell[rows][columns];


        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = new Cell(i, j, 1);
            }
        }

        this.observers =  new ArrayList<Observer>();
    }
}
