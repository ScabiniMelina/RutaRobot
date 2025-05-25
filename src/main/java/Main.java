import model.Grid;

import model.util.*;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");
        String filePath = "single_zero_path_5x6.json";
        int[][] gridValues  = JsonReader.readGridFromJSON(filePath);
        Grid grid = new Grid(gridValues);
        System.out.print(grid.toString());
    }
}