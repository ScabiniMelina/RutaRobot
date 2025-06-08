package model;

import observer.IObserver;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class GridTest {

    @Test
    void createValidGraphTest(){
        // Given
        int[][] validGrid = {{1,1,1},{-1,-1,-1}};
        // When
        Grid grid = new Grid(validGrid);
        // then
        assertEquals(grid.getGrid(), validGrid);
    }

    @Test
    void failOnCreateNullGridTest(){
        // Given
        int[][] validGrid = null;
        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Grid(validGrid);
        });
        // then
        assertEquals( "Grid cannot be null", exception.getMessage());
    }

    @Test
    void failOnCreateOddPathLenghtGridTest(){
        // Given
        int[][] validGrid = {{1}};
        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Grid(validGrid);
        });
        // then
        assertEquals( "Path length 1 is odd; must be even", exception.getMessage());
    }

    @Test
    void failOnCreateAGridWithInvalidNumbersTest(){
        // Given
        int[][] validGrid = {{2,1,1},{-1,-1,-1}};
        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Grid(validGrid);
        });
        // then
        assertEquals( "Invalid value at [0][0]: 2; only +1 or -1 allowed", exception.getMessage());
    }


    @Test
    void addingObserverSuccesfullyTest(){
        // Given
        int[][] validGrid = {{1,1,1},{-1,-1,-1}};
        Grid grid = new Grid(validGrid);
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
        Grid grid = new Grid(validGrid);

        // When
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            grid.addObserver(null);
        });

        // Then
        assertEquals("The observer cannot be null", exception.getMessage());
    }

    class DummyObserver implements IObserver {
        @Override
        public void notify(Grid grid) {
        }
    }
}