package model.util;

import com.google.gson.JsonParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    void nonExistentResource_throwsIOException() {
        String resourcePath = "non_existent.json";
        assertThrows(IOException.class, () -> JsonReader.readGridFromJSON(resourcePath),
                "Should throw IOException for non-existent resource");
    }

    @Test
    void validFileReadsCorrectGrid() throws IOException {
        String resourcePath = "multiple_zero_paths_5x6.json";
        int[][] grid = JsonReader.readGridFromJSON(resourcePath);
        assertNotNull(grid, "Grid should not be null");
        assertEquals(5, grid.length, "Grid should have 5 rows");
        assertEquals(6, grid[0].length, "Grid should have 6 columns");
        assertEquals(1, grid[0][0], "First cell should be 1");
        assertEquals(-1, grid[0][1], "Second cell should be -1");
    }

    @Test
    void emptyMatrixThrowsJsonParseException() {
        String resourcePath = "empty_matrix.json";
        assertThrows(JsonParseException.class, () -> JsonReader.readGridFromJSON(resourcePath),
                "Should throw JsonParseException for empty matrix");
    }

}