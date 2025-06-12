package model;

import observer.IObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;


class GridTest {

    @Test
    void createValidGraphTest(){
        // Given
        int[][] validGrid = {{1,1,1},{-1,-1,-1}};
        // When
        Grid grid = new Grid();
        grid.initGrid(validGrid);
        // then
        assertEquals(grid.getGrid(), validGrid);
    }

    @Test
    void failOnCreateNullGridTest(){
        // Given
        int[][] validGrid = null;
        // When
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            Grid grid = new Grid();
            grid.initGrid(validGrid);
        });
        // then
        assertEquals( "Cannot read the array length because \"this.gridMatrix\" is null", exception.getMessage());
    }

    @Test
    void failOnCreateAGridWithInvalidNumbersTest(){
        // Given
        int[][] validGrid = {{2,1,1},{-1,-1,-1}};
        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Grid grid = new Grid();
            grid.initGrid(validGrid);
        });
        // then
        assertEquals( "Invalid value at [0][0]: 2; only +1 or -1 allowed", exception.getMessage());
    }


    @Test
    void addingObserverSuccesfullyTest(){
        // Given
        int[][] validGrid = {{1,1,1},{-1,-1,-1}};
        Grid grid = new Grid();
        grid.initGrid(validGrid);
        IObserver observer = new DummyObserver();

        // When
        grid.addObserver(observer);

        // Then
        assertEquals(1, grid.getObservers().size());
    }

    @Test
    void failOnAddingNullObserverTest(){
        // Given
        int[][] validGrid = {{1,1,1},{-1,-1,-1}};
        Grid grid = new Grid();
        grid.initGrid(validGrid);

        // When
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            grid.addObserver(null);
        });

        // Then
        assertEquals("The observer cannot be null", exception.getMessage());
    }

    class DummyObserver implements IObserver {
        @Override
        public void update(String eventType) {

        }
    }
}