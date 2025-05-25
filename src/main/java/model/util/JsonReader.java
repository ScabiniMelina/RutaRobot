package model.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import model.Cell;
import model.Grid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonReader {

    public static List<Grid> readGridFromJSON(String filePath) throws IOException, JsonParseException {
        List<Grid> grid = new ArrayList<>();
        //int grid Cell[][] grid;
        try (InputStream inputStream = JsonReader.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IOException("JSON file not found in classpath: " + filePath);
            }

            // Read JSON content
            String content = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            // Parse JSON with Gson
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(content, JsonObject.class);
            JsonArray stationsArray = jsonObject.getAsJsonArray("x");

            // Parse each station
            for (int i = 0; i < stationsArray.size(); i++) {
                JsonObject stationObj = stationsArray.get(i).getAsJsonObject();

                int id = stationObj.get("id").getAsInt();
                String name = stationObj.get("name").getAsString();
                double x = stationObj.get("x").getAsDouble();
                double y = stationObj.get("y").getAsDouble();

            }
        } catch (JsonParseException e) {
            throw new JsonParseException("Error parsing JSON file: " + e.getMessage(), e);
        }

        return grid;
    }


}