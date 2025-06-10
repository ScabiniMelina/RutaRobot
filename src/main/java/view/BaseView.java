package view;

import view.util.Texts;

import javax.swing.*;
import java.awt.*;

import static javax.swing.BorderFactory.createEmptyBorder;
import static view.util.ColorPalette.BACKGROUND_DARK_BLUE;
import static view.util.ColorPalette.TEXT_WHITE_SOFT;
import static view.util.FontPalette.TITLE_FONT;
import static view.util.Texts.TITLE;

public class BaseView extends JFrame {

    public BaseView(){
        initializeFrame();
        setupLayout();
    }

    private void initializeFrame() {
        setTitle(TITLE);
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_DARK_BLUE.getColor());
        setLayout(new BorderLayout());
    }

    private void setupLayout() {
        add(createTitlePanel(), BorderLayout.NORTH);
    }

    private JPanel createTitlePanel() {
        JPanel titlePanel = createPanel(BACKGROUND_DARK_BLUE.getColor());
        titlePanel.setBorder(createEmptyBorder(60, 30, 0, 30));
        titlePanel.add(createTitleLabel(TITLE), BorderLayout.CENTER);
        return titlePanel;
    }

    private JPanel createPanel(Color backgroundColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);
        return panel;
    }

    private JLabel createTitleLabel(String title) {
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT.getFont());
        titleLabel.setForeground(TEXT_WHITE_SOFT.getColor());
        titleLabel.setOpaque(false);
        return titleLabel;
    }
}
