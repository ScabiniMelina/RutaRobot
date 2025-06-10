package view.util;

import java.awt.Color;

public enum ColorPalette {
    BACKGROUND_DARK_BLUE  (new Color(30, 42, 68)),
    CARD_COLOR (new Color(31, 73, 135)),
    TEXT_WHITE_SOFT  (new Color(230, 240, 250)),
    TEXT_GRAY (new Color(148, 163, 184)),
    GRID_LIGHT_GRAY (new Color(203, 213, 225)),
    GRID_MEDIUM_GRAY (new Color(156, 163, 175)),
    GRID_DARK_GRAY (new Color(107, 114, 128)),
    BUTTON_BLUE (new Color(77, 168, 218)),
    BUTTON_STATIONS_PURPLE (new Color(142, 68, 173)),
    TRAIL_MEDIUM_IMPACT (new Color(212, 184, 60)),
    TRAIL_LOW_IMPACT (new Color(106, 191, 75)),
    TRAIL_HIGH_IMPACT (new Color(230, 57, 70));

    private final Color color;

    ColorPalette(Color color){
        this.color = color;

    }

    public Color getColor(){
        return color;
    }
}

