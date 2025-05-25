package model.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class JsonReader {

    public void main() {
    }

    public static int[][] readGridFromJSON(String filePath) throws IOException, JsonParseException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String content = reader.lines().collect(Collectors.joining("\n"));

            // Parse JSON with Gson
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(content, JsonObject.class);
            JsonArray matrixArray = jsonObject.getAsJsonArray("matrix");

            // Convert JSON matrix to 2D int array
            int rows = matrixArray.size();
            if (rows == 0) {
                throw new JsonParseException("Empty matrix in JSON");
            }
            int cols = matrixArray.get(0).getAsJsonArray().size();
            int[][] grid = new int[rows][cols];

            for (int i = 0; i < rows; i++) {
                JsonArray row = matrixArray.get(i).getAsJsonArray();
                if (row.size() != cols) {
                    throw new JsonParseException("Inconsistent column count in matrix at row " + i);
                }
                for (int j = 0; j < cols; j++) {
                    grid[i][j] = row.get(j).getAsInt();
                }
            }

            return grid;

        } catch (JsonParseException e) {
            throw new JsonParseException("Error parsing JSON file: " + e.getMessage(), e);
        }
    }
}
