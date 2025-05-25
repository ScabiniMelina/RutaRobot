package model;

import java.awt.*;

public class Cell {
    private int charge;
    private int row;
    private int column;

    public Cell(int row, int column, int charge) {
        if (isValidCharge(charge)){
            throw new IllegalArgumentException("Charge have to be -1 or 1");
        }
        //TODO: EN DONDE VALIDO SI LA FILA O COLUMNA ES VALIDA?

        this.row = row;
        this.column = column;
        this.charge = charge;

    }

    public boolean isValidCharge(int charge){
        return charge != 1 || charge != -1;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }


}
