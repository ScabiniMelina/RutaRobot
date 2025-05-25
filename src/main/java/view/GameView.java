package view;

import controller.RobotController;
import model.Grid;
import observer.IObserver;
import view.util.ColorPalette;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame implements IObserver {
    private final RobotController robotController;

    public GameView(RobotController robotController){
        this.robotController = robotController;

        setTitle("Ruta Robot");
        setSize(1200, 800);
        setPreferredSize(new Dimension(1000, 800));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(ColorPalette.BACKGROUND_DARK_BLUE.getColor());

        add(createTitleLabel("A Jugar!"), BorderLayout.NORTH);

    }

    private JLabel createTitleLabel(String title) {
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(ColorPalette.TEXT_WHITE_SOFT.getColor());
        titleLabel.setOpaque(true);
        titleLabel.setBackground(ColorPalette.BACKGROUND_DARK_BLUE.getColor());
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        return titleLabel;
    }

    @Override
    public void notify(Grid grid) {

    }
}
