package model;

public enum BoardName {
    MULTIPLE_PATHS_WITH_RESULT_ZERO("Tablero con varios caminos que dan cero","src/main/resources/multiple_zero_paths_5x6.json"),
    NO_PATH_WITH_RESULT_ZERO("Tablero con ningun camino que da cero","src/main/resources/no_zero_path_5x6.json"),
    ODD_ERROR_PATH("Tablero que tiene error porque la cantidad de celdas es impar", "src/main/resources/odd_path_length_6x6.json"),
    SINGLE_PATH_WITH_RESULT_ZERO("Tablero con un único camino que da cero", "src/main/resources/single_zero_path_5x6.json");

    private  final String name;
    private final String path;

    BoardName(String name, String path) {
        this.name = name;
        this.path = path;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
