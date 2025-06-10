package view;

import controller.RobotController;
import view.util.Texts;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static view.util.ColorPalette.*;
import static view.util.FontPalette.*;

public class MainView extends BaseView {
    RobotController controller;
    JComboBox<String> selectedComboBox;

    public MainView(RobotController controller) {
        this.controller = controller;
        add(createMainCard(), BorderLayout.CENTER);
    }

    private JPanel createMainCard() {
        JPanel cardContainer = createGridBagPanel();
        cardContainer.setBorder(createEmptyBorder(15, 30, 50, 30));

        JPanel mainCard = createMainCardPanel();
        cardContainer.add(mainCard, createCenterConstraints());
        return cardContainer;
    }

    private JPanel createMainCardPanel() {
        JPanel mainCard = new JPanel();
        mainCard.setLayout(new BoxLayout(mainCard, BoxLayout.Y_AXIS));
        mainCard.setBackground(CARD_COLOR.getColor());
        mainCard.setBorder(createCardBorder());

        mainCard.add(createGridSelector());
        mainCard.add(Box.createVerticalStrut(25));
        mainCard.add(createButton());

        return mainCard;
    }

    private JPanel createGridSelector() {
        JPanel container = createPanel(CARD_COLOR.getColor());
        container.setMaximumSize(new Dimension(600, 100));

        container.add(createSelectorLabel(), BorderLayout.NORTH);
        container.add(createComboBox(), BorderLayout.CENTER);

        return container;
    }

    private JLabel createSelectorLabel() {
        JLabel label = new JLabel(Texts.boardSelection);
        label.setFont(LABEL.getFont());
        label.setForeground(TEXT_WHITE_SOFT.getColor());
        label.setBorder(createEmptyBorder(0, 8, 20, 0));
        return label;
    }

    private JComboBox<String> createComboBox() {
        String[] boards = controller.getBoardNames();
        selectedComboBox = new JComboBox<>(boards);

        setupComboBoxProperties(selectedComboBox);
        selectedComboBox.setRenderer(createComboBoxRenderer());
        selectedComboBox.setUI(createComboBoxUI());

        return selectedComboBox;
    }

    private void setupComboBoxProperties(JComboBox<String> combo) {
        combo.setFont(COMBO.getFont());
        combo.setPreferredSize(new Dimension(600, 55));
        combo.setBackground(TEXT_WHITE_SOFT.getColor());
        combo.setForeground(BACKGROUND_DARK_BLUE.getColor());
        combo.setBorder(createComboBorder());
    }

    private DefaultListCellRenderer createComboBoxRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBorder(createEmptyBorder(10, 16, 10, 16));
                setFont(COMBO.getFont());

                setBackground(isSelected ? BUTTON_BLUE.getColor() : TEXT_WHITE_SOFT.getColor());
                setForeground(isSelected ? TEXT_WHITE_SOFT.getColor() : BACKGROUND_DARK_BLUE.getColor());
                return this;
            }
        };
    }

    private BasicComboBoxUI createComboBoxUI() {
        return new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                return MainView.this.createArrowButton();
            }
        };
    }

    private JButton createArrowButton() {
        JButton arrowButton = new JButton("▼");
        setupArrowProperties(arrowButton);
        addHoverEffect(arrowButton, CARD_COLOR.getColor(), BUTTON_BLUE.getColor());
        return arrowButton;
    }

    private void setupArrowProperties(JButton arrowButton) {
        arrowButton.setFont(ARROW.getFont());
        arrowButton.setBackground(BUTTON_BLUE.getColor());
        arrowButton.setForeground(TEXT_WHITE_SOFT.getColor());
        arrowButton.setBorder(createArrowBorder());
        arrowButton.setFocusPainted(false);
        arrowButton.setPreferredSize(new Dimension(35, 55));
        arrowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        arrowButton.setOpaque(true);
    }

    private void addHoverEffect(JButton button, Color hoverColor, Color normalColor) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(normalColor);
            }
        });
    }

    private JPanel createButton() {
        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonContainer.setBackground(CARD_COLOR.getColor());
        buttonContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        buttonContainer.add(createStartButton());
        return buttonContainer;
    }

    private JButton createStartButton() {
        JButton startButton = new JButton(Texts.init);
        setupButtonProperties(startButton);
        addComplexHoverEffect(startButton);
        addButtonAction(startButton);
        return startButton;
    }

    private void setupButtonProperties(JButton button) {
        button.setFont(BUTTON.getFont());
        button.setPreferredSize(new Dimension(160, 40));
        button.setBackground(BUTTON_BLUE.getColor());
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(createButtonBorder());
    }

    private void addButtonAction(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedBoard = (String) selectedComboBox.getSelectedItem();
                openGameView(selectedBoard);
            }
        });
    }

    private void openGameView(String selectedBoard) {
        GameView gameView = new GameView(controller);
        gameView.setSelectedBoard(selectedBoard);
        gameView.setVisible(true);
        this.dispose();
    }

    private void addComplexHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(BUTTON_STATIONS_PURPLE.getColor());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(BUTTON_BLUE.getColor());
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                button.setBackground(BACKGROUND_DARK_BLUE.getColor());
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                button.setBackground(CARD_COLOR.getColor());
            }
        });
    }
    
    private JPanel createPanel(Color backgroundColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);
        return panel;
    }
    
    private JPanel createGridBagPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_DARK_BLUE.getColor());
        return panel;
    }
    
    private GridBagConstraints createCenterConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        return gbc;
    }
    
    private CompoundBorder createCardBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TEXT_GRAY.getColor(), 0),
            createEmptyBorder(30, 40, 30, 40)
        );
    }
    
    private CompoundBorder createComboBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TEXT_GRAY.getColor(), 0),
            createEmptyBorder(8, 8, 8, 8)
        );
    }
    
    private MatteBorder createArrowBorder() {
        return BorderFactory.createMatteBorder(0, 1, 0, 0, TEXT_GRAY.getColor());
    }
    
    private CompoundBorder createButtonBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CARD_COLOR.getColor(), 1),
            createEmptyBorder(8, 20, 8, 20)
        );
    }
    
    private Border createEmptyBorder(int top, int left, int bottom, int right) {
        return BorderFactory.createEmptyBorder(top, left, bottom, right);
    }
}
