package model;

import model.util.JsonReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class RobotTest {
    private Robot robot;
    private Grid validMultiplesSolutionsGrid, validSingleSolutionGrid, validNoPosibleSolutionsGrid;
    private Grid invalidGrid, invalidEmptyGrid ;

    @BeforeEach
    void setUp() throws IOException {
        int[][] validMultipleSolutionsGridData = JsonReader.readGridFromJSON("multiple_zero_paths_5x6.json");
        validMultiplesSolutionsGrid = new Grid(validMultipleSolutionsGridData);

        int[][] validSingleSolutionGridData = JsonReader.readGridFromJSON("single_zero_path_5x6.json");
       validSingleSolutionGrid = new Grid(validSingleSolutionGridData);

        int[][] validNoPosibleSolutionsGridData = JsonReader.readGridFromJSON("no_zero_path_5x6.json");
        validNoPosibleSolutionsGrid = new Grid(validNoPosibleSolutionsGridData);

        // Invalid 3x3 grid (all +1s, no zero-sum path)
        int[][] invalidGridData = {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };
        //invalidGrid = new Grid(invalidGridData);


    }

    @Test
    void canMoveRight_checksBoundary() {
        int[][] gridData = {{1, -1}, {-1, 1},{-1, 1}};
        Robot robot = new Robot(new Grid(gridData));
        assertTrue(robot.canMoveRight( 0), "Should allow move right from y=0");
        assertFalse(robot.canMoveRight( 1), "Should not allow move right from y=1");
    }

    @Test
    void canMoveDown_checksBoundary() {
        int[][] gridData = {{1, -1}, {-1, 1},{-1, 1}};
        Robot robot = new Robot(new Grid(gridData));
        assertTrue(robot.canMoveDown(1), "Should allow move down from x=1");
        assertFalse(robot.canMoveDown(2), "Should not allow move down from x=2");
    }


    @Test
    void getBestRouteWithoutPruningOnValidGridReturnsValidPath() throws Exception {
        robot = new Robot(validSingleSolutionGrid);
        List<Position> path = robot.getBestRouteWithoutPruning();
        assertEquals(10, path.size());
        assertTrue(isValidPath(path,  robot.getGrid()), "Path should be valid (right/down moves)");
        assertEquals(0, calculatePathSum(path,robot.getGrid()), "Path sum should be 0");
        assertEquals(new Position(4, 5), path.get(path.size() - 1), "Path should end at (4,5)");
    }

    @Test
    void getBestRouteWithPruningOnValidGridReturnsValidPath() throws Exception {
        robot = new Robot(validSingleSolutionGrid);
        List<Position> path = robot.getBestRouteWithPruning();
        assertEquals(10, path.size());
        assertTrue(isValidPath(path,  robot.getGrid()), "Path should be valid (right/down moves)");
        assertEquals(0, calculatePathSum(path,robot.getGrid()), "Path sum should be 0");
        assertEquals(new Position(4, 5), path.get(path.size() - 1), "Path should end at (4,5)");
    }

    @Test
    void getBestRouteWithoutPruningNoSolutionsGridThrowsException() throws Exception {
        Robot robot = new Robot(validNoPosibleSolutionsGrid);
        assertNull(robot.getBestRouteWithoutPruning(),
                "Should throw null for grid with no solutions");
    }

    @Test
    void getBestRouteWithPruningNoSolutionsGridThrowsException() throws Exception {
        Robot robot = new Robot(validNoPosibleSolutionsGrid);
        assertNull(robot.getBestRouteWithPruning(),
                "Should throw null for grid with no solutions");
    }

    @Test
    void pruningMetricsFewerRecursiveCallsThanNonPruning() throws Exception {
        robot = new Robot(validSingleSolutionGrid);
        robot.getBestRouteWithoutPruning();
        int nonPruningCalls = robot.getNonPruningMetrics().getRecursiveCalls();
        robot.getBestRouteWithPruning();
        int pruningCalls = robot.getPruningMetrics().getRecursiveCalls();
        assertTrue(pruningCalls <= nonPruningCalls, "Pruning should reduce recursive calls");
    }

    @Test
    void isAtDestinationCorrectlyIdentifiesTarget() {
        robot = new Robot(validSingleSolutionGrid);
        assertTrue(robot.isAtDestination(4,5), "Should identify (4,5) as destination");
        assertFalse(robot.isAtDestination(0, 0), "Should not identify (0,1) as destination");
    }

    private boolean isValidPath(List<Position> path, Grid grid) {
        if (path.isEmpty()) return false;
        Position prev = path.get(0);
        for (int i = 1; i < path.size(); i++) {
            Position curr = path.get(i);
            int dx = curr.getX() - prev.getX();
            int dy = curr.getY() - prev.getY();
            if (!((dx == 0 && dy == 1) || (dx == 1 && dy == 0))) {
                return false;
            }
            if (curr.getX() >= grid.getRows() || curr.getY() >= grid.getColumns()) {
                return false;
            }
            prev = curr;
        }
        return true;
    }

    private int calculatePathSum(List<Position> path, Grid grid) {
        int sum = 0;
        int[][] gridData = grid.getGrid();
        for (Position p : path) {
            sum += gridData[p.getX()][p.getY()];
        }
        return sum;
    }


}
