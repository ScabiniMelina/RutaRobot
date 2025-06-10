package view.util;

import java.awt.Font;

public enum FontPalette {
    TITLE_FONT(new Font("Segoe UI", Font.BOLD, 28)),
    LABEL(new Font("Segoe UI", Font.PLAIN, 14)),
    COMBO(new Font("Segoe UI", Font.PLAIN, 14)),
    BUTTON(new Font("Segoe UI", Font.BOLD, 14)),
    ARROW(new Font("Segoe UI", Font.PLAIN, 12));

    private final Font font;

    FontPalette(Font font) {
        this.font = font;
    }

    public Font getFont() {
        return font;
    }
} 